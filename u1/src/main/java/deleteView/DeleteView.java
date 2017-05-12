package deleteView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.Map;

/**
 * This class provides UI for delete action
 */
public class DeleteView extends BorderPane implements interfaces.IView {

    // YOU CAN SEE A MODIFIED SKELETON PATTERN!
    // tbis variable tracks the instantiated class objects, normally it should be 0 or 1
    private static int numInstances = 0;
    private static DeleteView v;
    public static DeleteView getInstance(){
        // if v isnt null and numInstances arent 0 well were going to duplicate it so we return null
        if(v != null && numInstances == 0) return null;
        // update, which excludes future instantiation
        numInstances++;
        // create new view
        v = new DeleteView();
        return v;
    }
    // well the key for singleton is a private ctor
    private DeleteView(){
        lvContent = new ListView();
        btDelete = new Button("Remove song");

        setCenter(lvContent);
        setBottom(btDelete);
    }
    // after the stage(aka window) ist closed we can reset it

    /**
     * Resets the singleton
     *
     */
    public static void closeView(){
        v = null;
        numInstances = 0;
    }

    private Button btDelete;
    private ListView lvContent;


    /**
     * Attach an event to delete button
     * @param eventHandler
     */
    public void addButtonDeleteEventHandler(EventHandler<ActionEvent> eventHandler ){
        btDelete.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Returns the selected Index
     * @return
     */
    public int getSelectedIndex(){ return lvContent.getSelectionModel().getSelectedIndex(); }


    public void setLocale(Map<String, String> locale) {
        // Check of corruption from localization
        if(locale != null && locale.size() == getChildren().size()){
            // TODO localization
        }
    }
}
