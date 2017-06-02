package controller;

import interfaces.*;

import core.*;

import view.AllSongsView;
import java.util.Observable;
import javafx.scene.input.MouseEvent;

/**
 * This class provides the controller functionality for the all song view
 */
public class AllSongsController implements interfaces.IController {

    private Model model;
    private AllSongsView view;
    private SongList allSongs, queue;
    // private SongList allSongs, queue;


    @Override
    public void link(IModel model, IView view){

        this.model = (Model)model;
        this.view = (AllSongsView) view;

        try {
            allSongs = this.model.getAllSongs();
            queue = this.model.getQueue();

            this.view.addButtonAddAllClickEventHandler(e -> buttonAddAllClicked(e));
            this.view.addListViewClickEventHandler(e -> listViewClicked(e));

            // Replaceable
            this.view.setListViewItem((SongList)allSongs);

        }catch (Exception ex){
            System.err.println("[E] Exception occurred in controller.AllSongsController.link(IModel, IView)");
            System.err.println(" *  " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Button click event handler
     * @param e MouseEvent
     */
    private void buttonAddAllClicked(MouseEvent e) {
        model.getQueue().addAll((SongList) view.getItems());
    }

    /**
     * ListView click event handler
     * @param e MouseEvent
     */
    private void listViewClicked(MouseEvent e) {
        try{
            if(e.getClickCount() == 2) queue.addAll((SongList)view.getSelectedItem());
            else if(e.getClickCount() == 4) allSongs.remove(view.getSelectedItem());
        } catch (Exception ex) {
            System.err.println("[E] Exception occurred in controller.AllSongsController.listViewClicked(MouseEvent)");
            System.err.println(" *  " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {}
}
