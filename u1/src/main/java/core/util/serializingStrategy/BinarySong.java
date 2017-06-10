package core.util.serializingStrategy;

import core.util.Song;

/**
 * Created by thahnen on 10.06.17.
 */
public class BinarySong {

    String path, album, interpret, title;
    long id;


    /**
     * Dafür da um aus einem Song mit Properties verständlichen Song zu machen
     * @param song
     */
    public BinarySong(Song song) {
        this.path = song.getPath();
        this.album = song.getAlbum();
        this.interpret = song.getInterpret();
        this.title = song.getTitle();
        this.id = song.getId();
    }


    public Song getSongClass() {
        return new Song(this.path, this.title, this.interpret, this.album, this.id);
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getInterpret() {
        return interpret;
    }

    public String getAlbum() {
        return album;
    }

    public long getId() {
        return id;
    }
}
