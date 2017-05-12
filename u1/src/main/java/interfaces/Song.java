package interfaces;

import javafx.beans.value.ObservableValue;

import java.io.Serializable;

/**
 * This interface provides basic information for every Song
 */
public interface Song {

    String getAlbum() ;

    void setAlbum(String album) ;

    String getInterpret() ;

    void setInterpret(String interpret) ;

    String getPath() ;

    void setPath(String path) ;

    String getTitle() ;

    void setTitle(String title) ;

    long getId();

    void setId(long id);

    ObservableValue<String> pathProperty();
    ObservableValue<String> albumProperty();
    ObservableValue<String> interpretProperty();

    String toString();
}
