package core;

import interfaces.ISong;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

/**
 * This class provides song information
 *
 */
public class Song implements ISong {

    private SimpleStringProperty album = new SimpleStringProperty("Unknown album"),
                                 interpret = new SimpleStringProperty("Unknown interpret"),
                                 path = new SimpleStringProperty(""),
                                 title = new SimpleStringProperty("Unknown title");
    private long id;
    private Image cover;

    public Song(long id){
        this.id = id;
    }

	public Song(String path, String title, String interpret, String album, long id) {
        this.path = new SimpleStringProperty(path);
        this.title = new SimpleStringProperty(title);
        this.interpret = new SimpleStringProperty(interpret);
        this.album = new SimpleStringProperty(album);
        this.id = id;
	}

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

    @Deprecated
    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public ObservableValue<String> pathProperty() {
        return interpret;
    }

    @Override
    public ObservableValue<String> albumProperty() {
        return album;
    }

    @Override
    public ObservableValue<String> interpretProperty() {
        return interpret;
    }

    @Override
    public String toString(){
        return getTitle();
    }

    public ObservableValue<String> titleProperty() {
        return title;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }
}
























// I sing a song for you its about silence, wanna hear?
// yeah go for it!
// <silence>