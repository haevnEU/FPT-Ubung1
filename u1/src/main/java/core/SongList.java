package core;

import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;

// TODO Rewrite comments

/**
 * This class provides a List of Song objects
 */
public class SongList extends ModifiableObservableListBase<interfaces.Song> implements interfaces.SongList  {

    ArrayList<interfaces.Song> songs2 = new ArrayList<>();
    ObservableList<interfaces.Song> songs;

    public SongList() {
        songs = FXCollections.observableArrayList();
    }

    /**
     * Adds a song into songlist
     * @param s
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addSong(interfaces.Song s) throws RemoteException {
        return songs.add(s);
    }

    /**
     * Deletes a song from the songlist
     * @param s
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean deleteSong(interfaces.Song s) throws RemoteException {
        return songs.remove(s);
    }

    /**
     * set current songlist
     * <p><i>if there is a songlist this will be overwritten </i></p>
     * @param s Neue Songlist
     * @throws RemoteException
     */
    @Override
    public void setList(ArrayList<interfaces.Song> s) throws RemoteException {
        this.songs2 = s;
    }

    /**
     * returns songlist
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<interfaces.Song> getList() throws RemoteException {
        return songs2;
    }

    /**
     * Deletes all songs inside songlist
     * @throws RemoteException
     */
    @Override
    public void deleteAllSongs() throws RemoteException {
        songs.clear();
    }

    /**
     * Returns the dimension of the songlist
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
    public interfaces.Song findSongByPath(String name) throws RemoteException {

        for(interfaces.Song s : songs)
            if(s.getPath().equals(name)) return s;
        return null;
    }

    /**
     * Returns a song using index
     * @param index
     * @return
     */
    @Override
    public interfaces.Song get(int index) {
        return songs.get(index);
    }

    /**
     * See sizeOfList
     * @return
     */
    @Deprecated
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
    protected void doAdd(int index, interfaces.Song element) {
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
    protected interfaces.Song doSet(int index, interfaces.Song element) {  return songs.set(index, element); }

    /**
     * Removes the element at position of {@code index}.
     *
     * @param index the index of the removed element
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    @Override
    protected interfaces.Song doRemove(int index) {
        return songs.remove(index);
    }
}
