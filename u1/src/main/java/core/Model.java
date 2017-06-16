package core;

// imports btw look at the bottom


import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import interfaces.ISongList;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import ApplicationException.IDOverFlowException;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class provides application data and logic
 */
public final class Model implements interfaces.IModel {

	private SongList queue;
	private SongList allSongs;
	private  Player player;

//     simple singleton skeleton => there is just one Model allowed
    private static Model instance;                                          // look at the imports
    public static Model getInstance(){
        if(instance == null) instance = new Model();
        return instance;
    }

    private Model(){
        queue = new SongList();
        allSongs = new SongList();
        player = Player.getInstance();
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

			    allSongs.add(song);
			    song.setTitle(f.getName());

			    // Receive metadata from mp3 file
			    m.getMetadata().addListener((MapChangeListener<String, Object>) metaData -> {
				    if (metaData.wasAdded()) {
					    if (metaData.getKey().equals("album")) song.setAlbum((metaData.getValueAdded().toString()));
					    else if (metaData.getKey().equals("artist"))
						    song.setInterpret(metaData.getValueAdded().toString());
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
	 * Set the queue
	 * @param queue new queue
	 */
	public void setQueue(SongList queue){
		try {
			this.queue.setList(queue.getList());
		} catch (RemoteException ex) {
			System.err.println("[CRIT] RemoteException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Set the all songs list
	 * @param allSongs new all song list
	 */
	public void setAllSongs(SongList allSongs){
		try {
			this.allSongs.setList(allSongs.getList());
		} catch (RemoteException ex) {
			System.err.println("[CRIT] RemoteException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
    }

    /**
     * Returns the current queue
     * @return queue
     */
    public SongList getQueue(){ return queue; }

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
    public SongList getAllSongs(){ return allSongs; }

	/**
	 * ...
	 * @return true if mediaPlayer is playing otherwise false
	 */
	public SimpleBooleanProperty getIsPlaying(){ return player.getIsPlaying(); }

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
	public void togglePlayPause(){
		if(!player.getInitialized()) callPlayerInit(getQueue());
		if(getIsPlaying().getValue()) player.pause();
		else player.play();
	}

	/**
	 * Stops a mp3 file
	 */
	public void stop(){
		player.stop();
	}

	/**
	 * Skips the current track
	 */
	public void skip(){
		player.skip();
	}


	/**
	 * Get the song length, note beta state
	 * @return song length as seconds
	 */
	public double getSongLength() {
		return player.getSongLength();
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