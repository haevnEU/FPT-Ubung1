package Classes;

import interfaces.Song;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class SongList implements interfaces.SongList {

    ArrayList<interfaces.Song> songs = new ArrayList<>();

    @Override
    public boolean addSong(Song s) throws RemoteException {
        return songs.add(s);
    }

    @Override
    public boolean deleteSong(Song s) throws RemoteException {
        return songs.remove(s);
    }

    @Override
    public void setList(ArrayList<Song> s) throws RemoteException {
        this.songs = s;
    }

    @Override
    public ArrayList<Song> getList() throws RemoteException {
        return songs;
    }

    @Override
    public void deleteAllSongs() throws RemoteException {
        for(int i = 0; i < songs.size(); i++) songs.remove(i);
    }

    @Override
    public int sizeOfList() throws RemoteException {
        return songs.size();
    }

    @Override
    public Song findSongByPath(String name) throws RemoteException {
        for(interfaces.Song s : songs){
            if(s.getTitle().equals(name)) return s;
        }

        return null;
    }

    @Override
    public Iterator<Song> iterator() {
        return null;
    }
}
