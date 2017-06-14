package interfaces;

import java.rmi.*;

import java.util.ArrayList;
import java.io.Serializable;

public interface ISongList extends Remote, Serializable, Iterable<ISong>{
    boolean addSong(ISong s) throws RemoteException;
    boolean deleteSong(ISong s) throws RemoteException;
    void setList(ArrayList<ISong> s) throws RemoteException;
    ArrayList<ISong> getList() throws RemoteException;
    void deleteAllSongs() throws RemoteException;
    int sizeOfList() throws RemoteException;
    ISong findSongByPath(String name) throws RemoteException;
}
