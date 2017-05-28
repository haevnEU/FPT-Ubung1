package interfaces;

import javafx.beans.value.ObservableValue;

/**
 * This interface is used as a blueprint for further song class
 */
public interface ISong {

    String getAlbum();

    void setAlbum(String album);

    String getInterpret();

    void setInterpret(String interpret);

    String getPath();

    void setPath(String path);

    String getTitle();

    void setTitle(String title);

    long getId();

    void setId(long id);

    ObservableValue<String> pathProperty();
    ObservableValue<String> albumProperty();
    ObservableValue<String> interpretProperty();

    String toString();

}
