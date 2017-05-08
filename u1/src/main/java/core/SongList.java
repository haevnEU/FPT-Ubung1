package core;

import interfaces.*;
import javafx.collections.ModifiableObservableListBase;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class SongList extends ModifiableObservableListBase<interfaces.Song> implements interfaces.SongList  {

    ArrayList<interfaces.Song> songs = new ArrayList<>();

    /**
     * Fügt ein core.Song in die Songlist
     * @param s hinzuzufügender core.Song
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean addSong(interfaces.Song s) throws RemoteException {
        return songs.add(s);
    }

    /**
     * Löscht ein core.Song aus der Songlist
     * @param s zu löschender core.Song
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean deleteSong(interfaces.Song s) throws RemoteException {
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
    public ArrayList<interfaces.Song> getList() throws RemoteException {
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
     * Sucht nach einem core.Song
     * @param name
     * @return Bei einer übereinstimmung gefundener core.Song ansonsten null
     * @throws RemoteException
     */
    @Override
    public interfaces.Song findSongByPath(String name) throws RemoteException {

        for(interfaces.Song s : songs)
            if(s.getPath().equals(name)) return s;
        return null;
    }

    @Override
    public interfaces.Song get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    /**
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
        songs.add(index, (interfaces.Song)element);
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
    protected interfaces.Song doSet(int index, interfaces.Song element) {
        return songs.set(index, (interfaces.Song)element);
    }

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
