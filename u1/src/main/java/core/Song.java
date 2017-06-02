package core;

import interfaces.ISong;

import javafx.beans.property.*;

import javafx.scene.image.Image;


/**
 * This class provides song information
 */
public final class Song implements ISong {

    private SimpleStringProperty album, interpret, path, title;
    private Image cover;
    private SimpleLongProperty id;

    /**
     * Constructor with default values
     */
    public Song(){
        album = new SimpleStringProperty("");
        interpret = new SimpleStringProperty("");
        title = new SimpleStringProperty("");
        path = new SimpleStringProperty("");
        id = new SimpleLongProperty(-1);
    }

    /**
     * "copy constructor, copies value from another song
     * @param s another song
     */
    public Song(Song s){
        album = s.albumProperty();
        interpret = s.interpretProperty();
        path = s.pathProperty();
        title = s.titleProperty();
        cover = s.getCover();
        id = s.idProperty();
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty pathProperty() {
        return path;
    }

    public SimpleStringProperty albumProperty() {
        return album;
    }

    public SimpleStringProperty interpretProperty() {
        return interpret;
    }

    @Override
    @Deprecated
    public String getAlbum() {
        return album.getValue();
    }

    @Override
    @Deprecated
    public void setAlbum(String album) { this.album = new SimpleStringProperty(album); }

    @Override
    @Deprecated
    public String getInterpret() {
        return interpret.getValue();
    }

    @Override
    @Deprecated
    public void setInterpret(String interpret) { this.interpret = new SimpleStringProperty(interpret); }

    @Override
    @Deprecated
    public String getPath() { return path.getValue(); }

    @Override
    @Deprecated
    public void setPath(String path) { this.path = new SimpleStringProperty(path); }

    @Override
    @Deprecated
    public String getTitle() {
        return title.getValue();
    }

    @Override
    @Deprecated
    public void setTitle(String title) { this.title = new SimpleStringProperty(title); }

    @Override
    @Deprecated
    public long getId() {
        return id.getValue();
    }

    @Override
    @Deprecated
    public void setId(long id) { this.id.set(id); }

    @Override
    public String toString(){
        return getTitle();
    }
}