package core.controller;

import javafx.beans.property.SimpleBooleanProperty;
import core.view.DeleteView;
import core.view.DetailView;
import core.util.Model;
import core.util.Song;
import core.util.Util;

import core.interfaces.IView;

import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.*;

import java.io.*;
import java.rmi.RemoteException;
import java.util.List;

// Here were are the MainView! Im happy!
/**
 * This class is used as a main core.controller
 */
public class Controller extends core.interfaces.IController{


    private core.view.View view;

    public void link(Model m, IView v){

        this.model = m;
        this.view = (core.view.View)v;

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

	    model.getIsPlaying().addListener((observable, oldValue, newValue) -> view.togglePlayPause(new SimpleBooleanProperty(newValue)));
		model.addEndOfMediaListener((observable, oldValue, newValue) ->
				view.setSongLength(model.getSongLength()));
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
            Util.showExceptionMessage(ex);
        }
    }

	/**
	 * Initialize the media player object
	 */
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
            Util.showExceptionMessage(ex);
        }
    }

    /**
     * EventHandler for DetailView
     */
    private void menuItemDetailEventHandler() {
		if(!createDetailWindow()) Util.showWarningMessage("Could not load detail window!\n\nDid you select something...");
    }

    /**
     * EventHandler for opening DeleteView
     */
    private void menuItemDeleteEventHandle() {
       if(!createDeleteWindow()) Util.showWarningMessage("Could not load delete window");
    }

    /**
     * This method toggles play and pause
     */
    private void playPause(){
		model.togglePlayPause();
		view.setSongLength(model.getSongLength());

    }

	/**
	 * Adds every son from AllSongs to queue
	 */
	private void addAll(){
        try {
            model.getQueue().addAll(model.getAllSongs().getList());
            initPlayer();
        } catch (RemoteException ex) {
           Util.showExceptionMessage(ex);
        }
    }

	/**
	 * Skips current song
	 */
	private  void skipSong(){ model.skip(); }

	/**
	 * Create and open a delete window
	 * @return false if the window could not be created
	 */
    private boolean createDeleteWindow(){
	    try {
			// Singleton part II access to the object!
		    DeleteView deleteView = DeleteView.getInstance(model.getQueue());
		    if (deleteView == null) return false;

		    core.controller.DeleteController deleteController = new core.controller.DeleteController();
		    deleteController.link(model, deleteView);


		    Stage deleteStage = new Stage();
		    deleteStage.setResizable(false);
		    deleteStage.setTitle("Delete");

		    // Singleton part II reset object... if we miss this call we cannot create new objects from this core.view
		    deleteStage.setOnCloseRequest(e -> deleteView.closeView());

		    Scene s = new Scene(deleteView);
		    deleteStage.setScene(s);
		    deleteStage.show();

	    } catch (Exception ex) { return false; }
	    return true;
    }

    /**
	 * Create and open a detail window, based on selected queue song
	 * @return false if the window could not be created
	 */
    private  boolean createDetailWindow(){

	    try {
		    DetailView detailView = DetailView.getInstance((Song)model.getQueue().get(view.getSelectedQueueIndex()));
		    if (detailView == null) return false;

		    DetailController detailController = new DetailController();
		    detailController.link(model, detailView);

		    Stage detailStage = new Stage();
		    detailStage.setWidth(250);
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
