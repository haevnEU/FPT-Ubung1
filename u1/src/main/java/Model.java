import core.InvalidFileException;
import core.Song;
import core.SongList;
import javafx.scene.media.Media;

import java.io.File;
import java.rmi.RemoteException;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Model {

    private SongList queue, allSongs;

    public Model(){
        queue = new SongList();
        allSongs = new SongList();
    }

    public void load(String path) throws InvalidFileException {
        File file = new File(path);
        if(!(file.exists() && file.isDirectory()))
            throw new InvalidFileException();
        int counter = 0;
        for(File f : file.listFiles()) {
            core.Song song = new Song();
            song.setPath(f.getAbsolutePath());
            song.setTitle(f.getName());
            song.setId(counter);
            song.setInterpret("TEST_INTERPRET");
            song.setAlbum("TEST_ALBUM");

            allSongs.add(song);
            counter++;
        }
    }

    public SongList getQueue(){ return queue; }

    public SongList getAllSongs(){ return allSongs; }

}
