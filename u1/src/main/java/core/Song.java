package core;

import javafx.beans.value.ObservableValue;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Song implements interfaces.Song {
    private String album, interpret, path, title;
    private long id;

    /**
     * DEBUG USAGE
     * @param album
     * @param interpret
     * @param path
     * @param title
     * @param id
     */
    public Song(String album, String interpret, String path, String title, long id){
        this.album = album;
        this.interpret = interpret;
        this.path = path;
        this.title = title;
        this.id = id;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String getInterpret() {
        return interpret;
    }

    @Override
    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

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
        Song copySong = new Song(null,null,null,null,0);
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
