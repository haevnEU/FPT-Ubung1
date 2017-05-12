package core;

// imports btw look at the bottom
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * This class provides application data and logic
 */
public class Model {

//     simple singleton skeleton
    private static Model instance;                                          // look at the imports
    public static Model getInstance(){
        if(instance == null) instance = new Model();
        return instance;
    }
    private Model(){
        queue = new SongList();
        allSongs = new SongList();
    }
//    end simple singleton skeleton


    private SongList queue, allSongs;
    private MediaPlayer player;


    /**
     * Method is used to load music files
     * @param path
     * @throws InvalidFileException
     */
    public void load(String path) throws InvalidFileException {

        File file = new File(path);

//         Test if file exists and its a directory
        if(!(file.exists() && file.isDirectory()))
            throw new InvalidFileException();
        int counter = 0;

//         iterate over every file inside the directory
        for(File f : file.listFiles()) {

//             filter mp3 file
            if(f.getName().endsWith("mp3")){

                Media m = new Media(f.getPath());
                // TODO read meta information from mp3 file using m (Media object)
                core.Song song = new Song();
                song.setPath(f.getAbsolutePath());
                song.setTitle(f.getName());
                song.setId(counter);
                song.setInterpret("TEST_INTERPRET");
                song.setAlbum("TEST_ALBUM");

                allSongs.add(song);
                queue.add(song);
                counter++;
            }
        }
    }

    /**
     * Returns the current queue
     * @return
     */
    public SongList getQueue(){ return queue; }

    /**
     * Returns every song in directory
     * @return
     */
    public SongList getAllSongs(){ return allSongs; }

}





















// youre close!










// look over there      x___________#
// is it a skeleton?    x______#
// NO ITS A CREEPER!!   x_#
// RUN AWAY! RUN LITTLE BOY!
// well it seems ... youre dead?
// creeper: 1 you: 0
// </ragequit> </fun>
// NICE HES ANGRY NOW! NO IM NOT ANGRY ITS JUST I I ...