package core;

import ApplicationException.UnknownApplicationException;
import interfaces.ISong;
import interfaces.ISongList;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;

// TODO Rewrite comments

/**
 * This class provides a List of ISong objects
 */
public class SongList extends ModifiableObservableListBase<ISong> implements ISongList {

    private final ObservableList<ISong> songs;

    public SongList() {
        songs = FXCollections.observableArrayList();
    }

    public SongList(ArrayList<interfaces.ISong> nsongs) {
        this.songs = FXCollections.observableList(nsongs);
    }

    /**
     * Adds a song into ISongList
     * @param s
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addSong(ISong s) throws RemoteException {
        try {
            IDGenerator.addId(s.getId());
        } catch (UnknownApplicationException ex) {
            System.err.println("[CRIT] Exception occured: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        return songs.add(s);
    }

    /**
     * Deletes a song from the ISongList
     * @param s
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean deleteSong(ISong s) throws RemoteException {
        return songs.remove(s);
    }

    /**
     * set current ISongList
     * <p><i>if there is a ISongList this will be overwritten </i></p>
     * @param s new ISongList
     * @throws RemoteException
     */
    @Override
    public void setList(ArrayList<ISong> s) throws RemoteException {
        songs.setAll(s);
    }

    /**
     * returns ISongList
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<ISong> getList() throws RemoteException {
        ArrayList<ISong> returnSongList = new ArrayList<>();
        returnSongList.addAll(songs);
        return returnSongList;
    }

    /**
     * Deletes all songs inside ISongList
     * @throws RemoteException
     */
    @Override
    public void deleteAllSongs() throws RemoteException {
        songs.clear();
    }

    /**
     * Returns the dimension of the ISongList
     * @return
     * @throws RemoteException
     */
    @Override
    public int sizeOfList() throws RemoteException {
        return songs.size();
    }

    /**
     * Search for specific song using path
     * @param name
     * @return If there is no match it will returned null otherwise it returns the song
     * @throws RemoteException
     */
    @Override
    public ISong findSongByPath(String name) throws RemoteException {

        for(ISong s : songs)
            if(s.getPath().equals(name)) return s;
        return null;
    }

    /**
     * Search for a song using id
     * @param id id
     * @return Song if there is a match otherwise null
     */
    public ISong findSongById(long id){
        for(ISong s : songs)
            if(s.getId() == id) return s;
        return null;
    }

    /**
     * Returns a song using index
     * @param index
     * @return
     */
    @Override
    public ISong get(int index) {
        return songs.get(index);
    }

    /**
     * See sizeOfList
     * @return
     */
    @Override
    public int size() {
        return songs.size();
    }

    /**
     * See addSong
     * Adds the {@code element} to the List at the position of {@code index}.
     * <p>
     * <p>For the description of possible exceptions, please refer to the documentation
     * of {@link #add(Object) } method.
     *
     * @param index   the position where to add the element
     * @param element the element that will be added
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    @Override
    protected void doAdd(int index, ISong element) {
        songs.add(index, element);
    }


    /**
     * Sets the {@code element} in the List at the position of {@code index}.
     * <p>
     * <p>For the description of possible exceptions, please refer to the documentation
     * of {@link #set(int, Object) } method.
     *
     * @param index   the position where to set the element
     * @param element the element that will be set at the specified position
     * @return the old element at the specified position
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    protected ISong doSet(int index, ISong element) {  return songs.set(index, element); }

    /**
     * Removes the element at position of {@code index}.
     *
     * @param index the index of the removed element
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    protected ISong doRemove(int index) {
        return songs.remove(index);
    }
}
