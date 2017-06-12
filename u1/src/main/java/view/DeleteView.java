package view;

import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import core.SongList;
import interfaces.ISong;
import javafx.geometry.Insets;

/**
 * This class provides UI for delete action
 */
public class DeleteView extends BorderPane implements interfaces.IView {

    private Button btDelete;
    private Button btDeleteAll;
    private ListView<ISong> lvContent;

    // this variable tracks the instantiated class objects, normally it should be 0 or 1
    private static int numInstances = 0;
    private static DeleteView v;
    public static DeleteView getInstance(SongList songs){
        // return null if there exists one instance of this class
        if(v != null && numInstances > 0) return null;
        // update, which excludes future instantiation
        numInstances++;
        // create new view
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

    @Override
    public void destroy() {
        numInstances--;
        v = null;
    }
}
