import com.sun.javafx.geom.Vec2d;
import core.Model;
import core.Song;
import core.util;

import deleteView.*;
import detailView.*;
import interfaces.IView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableListBase;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.util.Duration;

import java.io.*;
import java.rmi.RemoteException;
import java.util.List;

// Here were are the MainView! Im happy!
/**
 * This class is used as a main controller
 */
public class Controller extends interfaces.IController{


    private View view;

    public void link(Model m, IView v){

        this.model = m;
        this.view = (View)v;

        this.view.addLoadFilesClickEventHandler(e -> menuItemLoadEventHandler());
        this.view.addDetailClickEventHandler(e -> menuItemDetailEventHandler());
        this.view.DeleteClickEventHandler(e -> menuItemDeleteEventHandle());

        this.view.addButtonAllEventHandler(e -> addAll());
        this.view.addButtonPlayPauseEventHandler(e -> playPause());
        this.view.addButtonSkipEventHandler(e-> skipSong());

        this.view.addListViewAllSongClickEventHandler((e) -> listViewAllSongClicked(e));

        this.view.setAllSongs(model.getAllSongs());
        this.view.setQueue(model.getQueue());

	    view.togglePlayPause(model.getIsPlaying());

	    model.getIsPlaying().addListener((observable, oldValue, newValue) -> view.togglePlayPause());
		model.addEndOfMediaListener((observable, oldValue, newValue) ->
				view.setSliderMax(100));
    }


    /**
     * EventHandler for ListView click
     * @param e Object which triggered the event
     */
    private void listViewAllSongClicked(MouseEvent e){
        try {
            if(e.getClickCount() == 2){
            	model.getQueue().add(view.getSelectedSong());
				initPlayer();
            }
        } catch (Exception ex) {
            util.showExceptionMessage(ex);
        }
    }

    private void initPlayer(){
	    model.callPlayerInit(model.getQueue());
	    model.addTimeChangeListener((observable, oldValue, newValue) -> view.updateTime(newValue));

    }

    /**
     * Loads any directory
     */
    private void menuItemLoadEventHandler() {
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
            List<File> selectedFiles = fc.showOpenMultipleDialog(null);
            if (selectedFiles == null) return;
            model.loadAllSongs(selectedFiles);

        } catch (Exception ex) {
            core.util.showExceptionMessage(ex);
        }
    }

    /**
     * EventHandler for DetailView
     */
    private void menuItemDetailEventHandler() {
		if(!createDetailWindow()) {
			util.showWarningMessage("Could not load detail window!\n\nDid you select something...");
		}
    }

    /**
     * EventHandler for opening DeleteView
     */
    private void menuItemDeleteEventHandle() {
       if(!createDeleteWindow()) {
	       util.showWarningMessage("Could not load delete window");
       }
    }

    /**
     * This method toggles play and pause
     */
    private void playPause(){
		model.togglePlayPause();
    }

    private void addAll(){
        try {
            model.getQueue().addAll(model.getAllSongs().getList());
            initPlayer();
        } catch (RemoteException ex) {
           util.showExceptionMessage(ex);
        }
    }

    private  void skipSong(){
    	model.skip();
    }




	private Vec2d windowPosition(){
		double centerX = Main.getPrimaryStage().getX() + (Main.getPrimaryStage().getWidth() * 0.5);
		double centerY = Main.getPrimaryStage().getY() + (Main.getPrimaryStage().getHeight() * 0.5);
		return new Vec2d(centerX,centerY);
	}

    private boolean createDeleteWindow(){
	    try {

			Vec2d pos = windowPosition();    // position from window

			// Singleton part II access to the object!
		    deleteView.DeleteView deleteView = DeleteView.getInstance(model.getQueue());
		    if (deleteView == null) return false;

		    deleteView.DeleteController deleteController = new DeleteController();
		    deleteController.link(model, deleteView);


		    Stage deleteStage = new Stage();
		    // Set height and width and position on the right side
		    deleteStage.setHeight(Main.getPrimaryStage().getHeight() * 0.9);
		    deleteStage.setWidth(250);
		    deleteStage.setX(pos.x + (deleteStage.getWidth()));
		    deleteStage.setY(pos.y - (deleteStage.getHeight() * 0.5));
		    deleteStage.setResizable(false);
		    deleteStage.setTitle("Delete");

		    // Singleton part II reset object... if we miss this call we cannot create new objects from this view
		    deleteStage.setOnCloseRequest(e -> deleteView.closeView());

		    Scene s = new Scene(deleteView);
		    deleteStage.setScene(s);
		    deleteStage.show();

	    } catch (Exception ex) { return false; }
	    return true;
    }

    private  boolean createDetailWindow(){

	    try {
		    DetailView detailView = DetailView.getInstance((Song)model.getQueue().get(view.getSelectedQueueIndex()));
		    if (detailView == null) return false;

		    Vec2d pos = windowPosition();

		    DetailController detailController = new DetailController();
		    detailController.link(model, detailView);

		    Stage detailStage = new Stage();
		    detailStage.setWidth(250);
		    detailStage.setX(pos.x);
		    detailStage.setY(pos.y);
		    detailStage.setTitle("Details");
		    detailStage.setResizable(false);
		    detailStage.setAlwaysOnTop(true);
		    detailView.addButtonCommitEventHandler(e2-> detailStage.close());
			detailStage.setOnCloseRequest(e -> detailView.closeView());

		    Scene s = new Scene(detailView);
		    s.setOnKeyPressed((KeyEvent e2) -> {
			    if (e2.getCode() == KeyCode.ESCAPE) {
				    detailStage.close();
				    detailView.closeView();
			    }
		    });
		    detailStage.setScene(s);
		    detailStage.show();

	    } catch (Exception ex) {
	    	return false;
	    }
	    return  true;
    }

}
