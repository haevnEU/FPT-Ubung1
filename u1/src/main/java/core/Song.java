package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Song implements interfaces.Song {

    private SimpleStringProperty album, interpret, path, title;
    private long id;


    /**
     *
     * @return Albumname
     */
    @Override
    public String getAlbum() {
        return album.getValue();
    }

    /**
     * Setzt den Namen des Albums
     * @param album
     */
    @Override
    public void setAlbum(String album) { this.album = new SimpleStringProperty(album); }

    /**
     *
     * @return Interpetenname
     */
    @Override
    public String getInterpret() {
        return interpret.getValue();
    }

    /**
     * Aendern den Namen des Interpretens
     * @param interpret
     */
    @Override
    public void setInterpret(String interpret) { this.interpret = new SimpleStringProperty(interpret); }

    /**
     *
     * @return Pfad der Songdatei
     */
    @Override
    public String getPath() { return path.getValue(); }

    /**
     * Setzt den Songdateipfad
     * @param path
     */
    @Override
    public void setPath(String path) { this.path = new SimpleStringProperty(path); }

    /**
     *
     * @return Songtitel
     */
    @Override
    public String getTitle() {
        return title.getValue();
    }

    /**
     * Setzt den Titel des Songs
     * @param title
     */
    @Override
    public void setTitle(String title) { this.title = new SimpleStringProperty(title); }

    /**
     *
     * @return Song ID
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Setzt die Song ID
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
     * Gibt Informationen zu diesem Song
     * @return
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
