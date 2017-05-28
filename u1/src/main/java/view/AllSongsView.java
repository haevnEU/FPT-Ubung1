package view;

import interfaces.ISongList;

import javafx.scene.control.*;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import java.rmi.RemoteException;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;


/**
 * This class provides the view for all songs
 */
public class AllSongsView extends VBox implements interfaces.IView {


    private static AllSongsView instance;
    private AllSongsView(){}
    public static AllSongsView getInstance() {
        if(instance != null)  return null;
        instance = new AllSongsView();
        return instance;
    }
    @Override
    public void destroy() {
        instance = null;
    }

    private ListView listView;
    private Button btAddAll;

    @Override
    public void init() {
        try {
            listView = new ListView();
            btAddAll = new Button();
            btAddAll.setText("Add All");

            prefWidth(200);
            setPadding(new Insets(10));
            setSpacing(10);
            getChildren().addAll(listView,btAddAll);

        } catch (Exception ex) {
            System.err.println("[E] Exception occurred in view.AllSongsView.init");
            System.err.println(" *  " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * Bind the listView to a observable list
     * @param items which should be used as binding
     * @throws RemoteException ...
     */
    public void setListViewItem(ObservableList items) throws RemoteException {
        listView.setItems(items);
    }

    /**
     * Get the selected item
     * @return the selected item from listView
     */
    public Object getSelectedItem()
    {
        return listView.getSelectionModel().getSelectedItem();
    }

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event fired
     */
    public void addListViewClickEventHandler(EventHandler<MouseEvent> eventHandler){
        listView.setOnMouseClicked(eventHandler);
    }

    /**
     * Add a method for event handling
     * @param eventHandler method which should be invoked after this event fired
     */
    public void addButtonAddAllClickEventHandler(EventHandler<MouseEvent> eventHandler) {
        btAddAll.setOnMouseClicked(eventHandler);
    }

    /**
     * Returns every song inside this view
     * @return ISongList which represent all loaded songs
     */
    public ISongList getItems() {
        return (ISongList)listView.getItems();
    }
}
