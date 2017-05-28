package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This interface is used for further song list class
 */
public interface ISongList extends Remote, Serializable, Iterable<ISong>{

    boolean addSong(ISong s) throws RemoteException;

    boolean deleteSong(ISong s) throws RemoteException;

    void setList(ArrayList<ISong> s) throws RemoteException;

    ArrayList<ISong> getList() throws RemoteException;

    void deleteAllSongs() throws RemoteException;

    int sizeOfList() throws RemoteException;

    ISong findSongByPath(String name) throws RemoteException;
}
