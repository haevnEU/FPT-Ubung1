package core;
// TODO rewrite player class
// imports btw look at the bottom

import java.util.*;

import java.io.File;
import interfaces.IModel;
import interfaces.ISong;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import java.rmi.RemoteException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import java.rmi.server.UnicastRemoteObject;
import javafx.collections.MapChangeListener;
import applicationException.IDOverFlowException;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class provides application data and logic
 *
 * Written by Nils Milewski (nimile)
 */
public final class Model extends UnicastRemoteObject implements IModel {

	// helper variable with getter/setter
	private static boolean customDBFeature = false;
	public static void setCustomDBFeature(boolean customDBFeature) {
		Model.customDBFeature = customDBFeature;
	}
	public static boolean isCustomDBFeaturesEnabled() {
		return customDBFeature;
	}
	public SimpleBooleanProperty togglePlayPause = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty queueUpdate = new SimpleBooleanProperty(false);
	private SongList slQueue, slLibrary;
	private  Player player;
	private RMIBroadcaster rmiBroadcaster;
	private List<IModel> clients = new ArrayList<>();
//     simple singleton skeleton => there is just one Model allowed
    private static Model instance;
    // look at the imports
    public static Model getInstance(){
    	return getInstance(null);
    }
	public static Model getInstance(List<IModel> clients){
        if(instance == null) try {
	        instance = new Model(clients);
        } catch (RemoteException e) {
	        e.printStackTrace();
        }
	    return instance;
    }

    @Override
    public void notifyPlay(){
Platform.runLater(()->		togglePlayPause.setValue(false));
    }

    @Override
    public void notifyPause() {
	    Platform.runLater(() -> togglePlayPause.setValue(true));
    }

    public void add(IModel client){
    	clients.add(client);
    }

    public SimpleBooleanProperty getQueueUpdate(){ return queueUpdate; }

    private Model(List<IModel> clients) throws RemoteException {
	    super();
	    rmiBroadcaster = new RMIBroadcaster(clients, this);
	    slQueue = new SongList();
        slLibrary = new SongList();
        player = Player.getInstance(rmiBroadcaster);
        this.clients = clients;
    }

    @Override
    public boolean getPlaying() throws RemoteException{
		return player.getIsPlaying().getValue();
    }

	public RawList getRawLibrary() throws RemoteException{
    	return new RawList(getLibrary());
	}

	public RawList getRawQueue() throws RemoteException{
    	return new RawList(getQueue());
	}

	@Override
	public void updateQueueView(RawList list) throws RemoteException {
		if(CVars.isClientEnabled()) {
		    slQueue.clear();
		    for (ISong s : list.getSongList().getList()) {
			    System.out.println("[INFO] adding " + s.getTitle() + " to queue.");
			    slQueue.add(s);
		    }
		    queueUpdate.set(true);
	    }
	}

	@Override
	public void addToQueue(SongWrapper song) throws RemoteException {
		Song s = new Song(song.getId());
		s.setAlbum(song.getAlbum());
		s.setInterpret(song.getArtist());
		s.setPath(song.getPath());
		s.setTitle(song.getTitle());

		Platform.runLater(() -> {
			getQueue().add(s);
		System.out.println("BEFORE BROADCAST " + getQueue().size());
		for (IModel client : clients)
		{
			try {
				RawList r = new RawList(getQueue());
				client.updateQueueView(r);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}});
	}

	@Override
	public void notifySongChange(SongWrapper song) throws RemoteException {
		ISong s = new Song(song.getId());
		s.setPath(song.getPath());
		s.setInterpret(song.getArtist());
		s.setAlbum(song.getAlbum());
		s.setTitle(song.getTitle());
		getLibrary().getList().set(Math.toIntExact(song.getId()), s);
		getQueue().getList().set(Math.toIntExact(song.getId()), s);
		getQueue().findSongById(s.getId()).setTitle(s.getTitle());
		getQueue().findSongById(s.getId()).setAlbum(s.getAlbum());
		getQueue().findSongById(s.getId()).setInterpret(s.getInterpret());
		getQueue().findSongById(s.getId()).setPath(s.getPath());
		System.out.println("[INFO] Updated: " + s.getTitle());
	}

	public String getTime() {

    	return ""+((int)player.getTime());
	}

	/**
     * Method is used to loadAllSongsFromFile music files
     * @param files files which should be loaded
     */
    public void loadAllSongsFromFile(List<File> files) {
	    try {

//         iterate over every file inside the directory
		    for (File f : files) {
			    core.Song song = new Song(IDGenerator.getNextID());
			    Media m = new Media(f.toURI().toString());

			    song.setPath(f.toURI().toString());

			    slLibrary.add(song);
			    song.setTitle(f.getName());

			    // Receive metadata from mp3 file
			    m.getMetadata().addListener((MapChangeListener<String, Object>) metaData -> {
				    if (metaData.wasAdded()) {
					    if (metaData.getKey().equals("album")) song.setAlbum((metaData.getValueAdded().toString()));
					    else if (metaData.getKey().equals("artist")) song.setInterpret(metaData.getValueAdded().toString());
					    else if (metaData.getKey().equals("title")) song.setTitle(metaData.getValueAdded().toString());
					    else if (metaData.getKey().equals("image")) song.setCover((Image) metaData.getValueAdded());
				    }
			    });
		    }
	    } catch (IDOverFlowException e) {
		    System.err.println("[SYS CRIT] IDOverflow exception occurred at " + e.getMessage());
	    }
    }

    /**
     * Returns the current slQueue
     * @return slQueue
     */
    public SongList getQueue(){
	    return slQueue;
    }

	/**
	 * Initialize the media player
	 * @param queue list of songs
	 */
	public void callPlayerInit(SongList queue){
		player.init(queue);
    }

    /**
     * Returns every song in directory
     * @return every loaded song
     */
    public SongList getLibrary(){
	    return slLibrary;
    }

	/**
	 * ...
	 * @return true if mediaPlayer is playing otherwise false
	 */
	public SimpleBooleanProperty getIsPlaying(){
	    return player.getIsPlaying();
	}

	/**
	 * Add event handler for TimeChange events
	 * @param e method which should be called
	 */
	public void addTimeChangeListener(ChangeListener<Duration> e){
		player.addTimeChangeListener(e);
	}

	/**
	 * Add event handler for EOM events
	 * @param e method which should be called
	 */
	public void addEndOfMediaListener(ChangeListener<Boolean> e){
		player.addEndOfMediaListener(e);
	}

	/**
	 * Plays a mp3 file
	 */
	synchronized public void togglePlayPause(){
		Platform.runLater(() -> {
			if(!player.getInitialized()) callPlayerInit(getQueue());
			if(getIsPlaying().getValue()) {
				player.pause();
				rmiBroadcaster.broadcastPlayPause(true);
			}
			else{
				player.play();
				rmiBroadcaster.broadcastPlayPause(false);
			}
		});
	}

	/**
	 * Stops a mp3 file
	 */
	public void stop(){
		Platform.runLater(() -> {
			player.stop();
			rmiBroadcaster.broadcastPlayPause(true);
		});
	}

	/**
	 * Skips the current track
	 */
	public void skip() {
		Platform.runLater(() -> player.skip());
	}


	/**
	 * Get the song length, note beta state
	 * @return song length as seconds
	 */
	public double getSongLength() {
		return player.getSongLength();
	}

	public String getCurrentSong() {
		return getQueue().get(0).getTitle();
	}


}


























// you are close!
















































































































































































































































































































































































































































































































































































































































































































































































































































// look over there      x___________#
// is it a skeleton?    x______#
// NO ITS A CREEPER!!   x_#
// RUN AWAY! RUN LITTLE BOY!
// well it seems ... you are dead?
// creeper: 1 you: 0
// </rage quit> </fun>
// NICE HES ANGRY NOW! NO IM NOT ANGRY ITS JUST I I ...