package core.util;

// imports btw look at the bottom

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;
import java.util.List;


/**
 * This class provides application data and logic
 */
public class Model {

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
     * Method is used to loadAllSongs music files
     * @param files files which should be loaded
     */
    public void loadAllSongs(List<File> files) {
        int counter = 0;

//         iterate over every file inside the directory
        for(File f : files){
	        core.util.Song song = new Song();

            Media m = new Media(f.toURI().toString());

	        song.setPath(f.toURI().toString());
            song.setId(counter);

	        allSongs.add(song);
	        song.setTitle(f.getName());

	        // Receive metadata from mp3 file
            m.getMetadata().addListener((MapChangeListener<String, Object>) metaData -> {
	            if (metaData.wasAdded()) {
		            if (metaData.getKey().equals("album")) song.setAlbum((metaData.getValueAdded().toString()));
		            else if (metaData.getKey().equals("artist")) song.setInterpret(metaData.getValueAdded().toString());
		            else if (metaData.getKey().equals("title"))  song.setTitle(metaData.getValueAdded().toString());
	            }
            });

            counter++;
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