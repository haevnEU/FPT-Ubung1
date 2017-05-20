package core;

// imports btw look at the bottom

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.media.Media;
import javafx.util.Duration;

import java.io.File;
import java.util.List;


/**
 * This class provides application data and logic
 */
public class Model {
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

    private SongList queue;
    private SongList allSongs;

    /**
     * Method is used to loadAllSongs music files
     * @param files files which should be loaded
     */
    public void loadAllSongs(List<File> files) {
        int counter = 0;

//         iterate over every file inside the directory
        for(File f : files){
	        core.Song song = new Song();

            Media m = new Media(f.toURI().toString());

	        song.setPath(f.toURI().toString());
            song.setId(counter);
            song.setTitle(m.getMetadata().getOrDefault("title",f.getName()).toString());
	        song.setInterpret(m.getMetadata().getOrDefault("artist","Unknown Interpret").toString());
            song.setAlbum(m.getMetadata().getOrDefault("album","Unknown album").toString());


            allSongs.add(song);
            counter++;
        }
    }

    /**
     * Returns the current queue
     * @return queue
     */
    public SongList getQueue(){ return queue; }

    public void callPlayerInit(SongList queue){
    	player.init(queue);
    }

    /**
     * Returns every song in directory
     * @return every loaded song
     */
    public SongList getAllSongs(){ return allSongs; }

	public SimpleBooleanProperty getIsPlaying(){ return player.getIsPlaying(); }

	public void addTimeChangeListener(ChangeListener<Duration> e){
		player.addTimeChangeListener(e);
	}

	public void addEndOfMediaListener(ChangeListener<Boolean> e){
		player.addEndOfMediaListener(e);
	}

	/**
	 * Plays a mp3 file
	 */
	public void togglePlayPause(){
		if(player.getIsPlaying().getValue())
			player.pause();
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