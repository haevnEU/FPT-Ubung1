package core.view;

import core.util.SongList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Map;

/**
 * This class provides UI for delete action
 */
public class DeleteView extends BorderPane implements core.interfaces.IView {

    private Button btDelete;
    private Button btDeleteAll;
    private ListView<core.interfaces.Song> lvContent;

    // this variable tracks the instantiated class objects, normally it should be 0 or 1
    private static int numInstances = 0;
    private static DeleteView v;
    public static DeleteView getInstance(SongList songs){
        // return null if there exists one instance of this class
        if(v != null && numInstances > 0) return null;
        // update, which excludes future instantiation
        numInstances++;
        // create new core.view
        v = new DeleteView(songs);
        return v;
    }
    // private constructor
    private DeleteView(SongList songs){
        lvContent = new ListView<>();
        lvContent.setItems(songs);
        btDelete = new Button("Remove song");
        btDeleteAll = new Button("Remove all");

        HBox btBox = new HBox();
        btBox.setSpacing(10);
        btBox.setPadding(new Insets(10));
        btBox.getChildren().addAll(btDelete,btDeleteAll);

        setPadding(new Insets(10));
        setCenter(lvContent);

        setBottom(btBox);
    }
    // after the stage(aka window) ist closed we can reset it

    /**
     * Resets the singleton
     */
    public void closeView(){
        v = null;
        numInstances = 0;
    }

    /**
     * Attach an event to delete button
     * @param eventHandler method which should handle the button click event
     */
     public void addButtonDeleteEventHandler(EventHandler<ActionEvent> eventHandler ){
        btDelete.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Attach an event to delete all button
     * @param eventHandler method which should handle the button click event
     */
     public void addButtonDeleteAllEventHandler(EventHandler<ActionEvent> eventHandler){
        btDeleteAll.addEventHandler(ActionEvent.ACTION, eventHandler);
     }

    /**
     * Returns the selected Index
     * @return selected index inside the deleteView
     */
    public int getSelectedIndex(){ return lvContent.getSelectionModel().getSelectedIndex(); }


    public void setLocale(Map<String, String> locale) {

    }
}
