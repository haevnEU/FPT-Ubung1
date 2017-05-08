package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Song implements interfaces.Song {
    private SimpleStringProperty album, interpret, path, title;
    private long id;


    @Override
    public String getAlbum() {
        return album.getValue();
    }

    @Override
    public void setAlbum(String album) { this.album = new SimpleStringProperty(album); }

    @Override
    public String getInterpret() {
        return interpret.getValue();
    }

    @Override
    public void setInterpret(String interpret) { this.interpret = new SimpleStringProperty(interpret); }

    @Override
    public String getPath() { return path.getValue(); }

    @Override
    public void setPath(String path) { this.path = new SimpleStringProperty(path); }

    @Override
    public String getTitle() {
        return title.getValue();
    }

    @Override
    public void setTitle(String title) { this.title = new SimpleStringProperty(title); }

    @Override
    public long getId() {
        return id;
    }

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





    @Override
    public String toString(){
        return "Title: " + getTitle()
                + " Album: " + getAlbum()
                + " Interpret: " + getInterpret()
                + " ID: " + getId()
                + " Path: " + getPath() ;
    }

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

    @Override
    public boolean equals(Object another){
        return ((Song)another).getId() == this.getId();
    }
}
