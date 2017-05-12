package core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Model {

    private SongList queue, allSongs;

    MediaPlayer player;
    public Model(){
        queue = new SongList();
        allSongs = new SongList();
    }

    /**
     * Method is used to load music files
     * @param path
     * @throws InvalidFileException
     */
    public void load(String path) throws InvalidFileException {

        File file = new File(path);
        // Test if file exists and its a directory
        if(!(file.exists() && file.isDirectory()))
            throw new InvalidFileException();
        int counter = 0;

        // iterate over every file inside the directory
        for(File f : file.listFiles()) {

            // filter mp3 file
            if(f.getName().endsWith("mp3")){

                // TODO read metainformation from mp3 file
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

    public SongList getQueue(){ return queue; }

    public SongList getAllSongs(){ return allSongs; }

}
