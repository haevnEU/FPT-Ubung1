import javafx.beans.value.ObservableValue;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Song implements interfaces.Song {
    private String album, interpret, path, title;
    private long id;

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
}
