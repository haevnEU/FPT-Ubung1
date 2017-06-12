package core.util.serializingStrategy;

import core.interfaces.Song;
import core.util.SongList;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by thahnen on 10.06.17.
 * Für Übungsblatt 2 die Binary-Serialisierung
 *  => dazu müssen SongList/ Song -Klassen umgewandelt werden
 *  => mal gucken
 */
public class BinarySongList {

    private ArrayList<BinarySong> songs;


    /**
     * SongList mit ObservableValues in serialisierbare Version umwandeln.
     * @param songList
     */
    public BinarySongList(SongList songList) throws RemoteException {
        this.songs = new ArrayList<>();
        for (Song item : songList.getList()) {
            this.songs.add(new BinarySong((core.util.Song)item));
        }
    }


    public SongList getSongList() {
        ArrayList<Song> nsongs = new ArrayList<>();
        for (BinarySong item : this.songs) {
            nsongs.add(
                    new core.util.Song(item.getPath(), item.getTitle(), item.getInterpret(), item.getPath(), item.getId())
            );
        }
        return new SongList(nsongs);
    }

    class BinarySong {

        String path, album, interpret, title;
        long id;


        /**
         * Dafür da um aus einem Song mit Properties verständlichen Song zu machen
         * @param song
         */
        public BinarySong(core.util.Song song) {
            this.path = song.getPath();
            this.album = song.getAlbum();
            this.interpret = song.getInterpret();
            this.title = song.getTitle();
            this.id = song.getId();
        }


        public core.util.Song getSongClass() {
            return new core.util.Song(this.path, this.title, this.interpret, this.album, this.id);
        }

        public String getPath() { return path; }

        public String getTitle() { return title; }

        public String getInterpret() { return interpret; }

        public String getAlbum() { return album; }

        public long getId() { return id; }
    }
}
