import core.SongList;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Model {

    private SongList queue, allSongs;

    public Model(){

    }

    public SongList getQueue(){ return queue; }

    public SongList getAllSongs(){ return allSongs; }

}
