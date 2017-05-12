package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

// TODO Rewrite comments

/**
 * This class provides song information
 */
public class Song implements interfaces.Song {

    private SimpleStringProperty album, interpret, path, title;
    private long id;


    /**
     * @return Name of album
     */
    @Override
    public String getAlbum() {
        return album.getValue();
    }

    /**
     * Set the name of the song album
     * @param album
     */
    @Override
    public void setAlbum(String album) { this.album = new SimpleStringProperty(album); }

    /**
     *
     * @return Name of interpret
     */
    @Override
    public String getInterpret() {
        return interpret.getValue();
    }

    /**
     * Set name of interpret
     * @param interpret
     */
    @Override
    public void setInterpret(String interpret) { this.interpret = new SimpleStringProperty(interpret); }

    /**
     *
     * @return Path of song
     */
    @Override
    public String getPath() { return path.getValue(); }

    /**
     * Set the path to the song
     * @param path
     */
    @Override
    public void setPath(String path) { this.path = new SimpleStringProperty(path); }

    /**
     *
     * @return Return title of song
     */
    @Override
    public String getTitle() {
        return title.getValue();
    }

    /**
     * Set title of song
     * @param title
     */
    @Override
    public void setTitle(String title) { this.title = new SimpleStringProperty(title); }

    /**
     * @return Song ID
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Set song ID
     * @param id
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }


    // TODO Implement following methods
    @Override
    public ObservableValue<String> pathProperty() {
        return null;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return null;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return null;
    }


    /**
     *  @return
     */
    @Override
    public String toString(){
        return "Title: " + getTitle()
                + " Album: " + getAlbum()
                + " Interpret: " + getInterpret()
                + " ID: " + getId()
                + " Path: " + getPath()
                + "\nName and Location: " + super.toString();
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object.
     */
    @Override
    public Song clone(){
        Song copySong = new Song();
        copySong.setAlbum(getAlbum());
        copySong.setId(getId());
        copySong.setInterpret(getInterpret());
        copySong.setPath(getPath());
        copySong.setTitle(getTitle());
        return copySong;
    }

    /**
     * Prueft ein anderes Objekt mit diesem auf Gleicheit
     * @param another
     * @return wahr bei absoluter gleichheit
     */
    @Override
    public boolean equals(Object another){
        return another != null && ((Song)another).getId() == this.getId();
    }
}
























// I sing a song for you its about silence, wanna hear?
// yeah go for it!
// <silence>