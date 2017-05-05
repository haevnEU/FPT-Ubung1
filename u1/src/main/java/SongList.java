import interfaces.*;
import interfaces.Song;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class SongList implements interfaces.SongList {

    ArrayList<interfaces.Song> songs = new ArrayList<>();

    /**
     * Fügt ein Song in die Songlist
     * @param s hinzuzufügender Song
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addSong(interfaces.Song s) throws RemoteException {
        return songs.add(s);
    }

    /**
     * Löscht ein Song aus der Songlist
     * @param s zu löschender Song
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean deleteSong(Song s) throws RemoteException {
        return songs.remove(s);
    }

    /**
     * Setzt die aktuelle Songliste, falls eine existiert wird diese überschrieben
     * @param s Neue Songlist
     * @throws RemoteException
     */
    @Override
    public void setList(ArrayList<interfaces.Song> s) throws RemoteException {
        this.songs = s;
    }

    /**
     * Gibt die Songliste zurück
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Song> getList() throws RemoteException {
        return songs;
    }

    /**
     * Löscht alle Songs in der Songliste
     * @throws RemoteException
     */
    @Override
    public void deleteAllSongs() throws RemoteException {
        songs.clear();
    }

    /**
     * Gibt die Größe der Songliste zurück
     * @return
     * @throws RemoteException
     */
    @Override
    public int sizeOfList() throws RemoteException {
        return songs.size();
    }

    /**
     * Sucht nach einem Song
     * @param name
     * @return Bei einer übereinstimmung gefundener Song ansonsten null
     * @throws RemoteException
     */
    @Override
    public interfaces.Song findSongByPath(String name) throws RemoteException {

        for(interfaces.Song s : songs)
            if(s.getTitle().equals("name")) return s;
        return null;
    }
}
