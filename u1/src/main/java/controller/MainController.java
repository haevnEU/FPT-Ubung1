package controller;

import javafx.event.ActionEvent;
import view.*;
import core.*;
import interfaces.*;
import javafx.stage.*;
import javafx.scene.input.*;

import javafx.scene.Scene;
import java.rmi.RemoteException;
import javafx.scene.layout.BorderPane;
import applicationException.RichException;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class provides functionality for the main view
 *
 * written by Nils Milewski (nimile)
 */
public class MainController implements IController{

    private MainView view;
	private Model model;

	@Override
    public void link(IModel m, IView v){

        this.model = (Model)m;
        this.view = (MainView)v;

        this.view.addLoadFilesClickEventHandler(this::menuItemLoadEventHandler);
        this.view.addDetailClickEventHandler(this::menuItemDetailEventHandler);
        this.view.DeleteClickEventHandler(this::menuItemDeleteEventHandle);


        this.view.addButtonAllEventHandler(this::addAll);
        this.view.addButtonPlayPauseEventHandler(this::playPause);
        this.view.addButtonSkipEventHandler(this::skipSong);
        this.view.addListViewAllSongClickEventHandler(this::listViewAllSongClicked);
		this.view.addButtonSaveEventHandler( e-> Util.invokeNewWindow(SceneType.SaveView, model));

        this.view.setAllSongs(model.getLibrary());
        this.view.setQueue(model.getQueue());

	    view.togglePlayPause(model.getIsPlaying());

	    model.getIsPlaying().addListener((observable, oldValue, newValue) -> view.togglePlayPause(new SimpleBooleanProperty(newValue)));
		model.addEndOfMediaListener((observable, oldValue, newValue) -> view.setSongLength(model.getSongLength()));

	}
	/**
     * EventHandler for ListView click
     * @param e Object which triggered the event
     */
    private void listViewAllSongClicked(MouseEvent e){
       if(e.isAltDown() && e.getClickCount() == 2) Util.invokeNewWindow(SceneType.DetailView, model, this.view.getSelectedSong());
       else if(e.getClickCount() == 2){
	       model.getQueue().add(view.getSelectedSong());
	       initPlayer();
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
    private void menuItemLoadEventHandler(ActionEvent actionEvent) {
		Util.invokeNewWindow(SceneType.LoadView, model);
    }

    /**
     * EventHandler for DetailView
     */
    private void menuItemDetailEventHandler(ActionEvent actionEvent) {Util.invokeNewWindow(SceneType.DetailView, model, this.view.getSelectedSong());
	 }

    /**
     * EventHandler for opening DeleteView
     */
    private void menuItemDeleteEventHandle(ActionEvent actionEvent) {
        Util.invokeNewWindow(SceneType.DeleteView, model);
    }

    /**
     * This method toggles play and pause
     */
    private void playPause(ActionEvent actionEvent){
		model.togglePlayPause();
		view.setSongLength(model.getSongLength());
    }

	/**
	 * Adds every son from AllSongs to queue
	 */
	private void addAll(ActionEvent actionEvent){
        try {
            model.getQueue().addAll(model.getLibrary().getList());
            initPlayer();
        } catch (RemoteException ex) {
	        System.err.println("[CRIT] RemoteException occurred at " + Util.getUnixTimeStamp());
            ex.printStackTrace(System.err);
            new RichException(ex);
        }
    }

	/**
	 * Skips current song
	 */
	private  void skipSong(ActionEvent actionEvent){ model.skip(); }

	/**
	 * Invokes any window
	 * @param t which scene should be invoked, details could be seen inside view.SceneType
	 */
	@Deprecated
	private void invokeNewWindows(SceneType t){

		Scene tmpScene = new Scene(new BorderPane());
		Stage tmpStage = new Stage();
		IController tmpController;
		IView tmpView = new FileViewer();
		switch(t){
			case DeleteView:
				tmpController = new DeleteController();
				tmpView = DeleteView.getInstance(model.getQueue());
				if(tmpView == null) return;
				tmpScene = new Scene((DeleteView)tmpView);

				tmpController.link(model, tmpView);
				tmpStage.setTitle("Delete...");
				tmpStage.setWidth(250);
				tmpScene.setOnKeyPressed(e -> {
					if(e.getCode() == KeyCode.ESCAPE) tmpStage.close();
				});
				break;

			case DetailView:
				Song s = this.view.getSelectedSong();
				tmpController = new DetailController();
				tmpView = new DetailView();
				tmpScene = new Scene((DetailView)tmpView);

				tmpController.link(model, tmpView);
				((DetailController)tmpController).init(s);

				tmpStage.setTitle(s.getTitle());
				tmpScene.setOnKeyPressed(e -> {
					if(e.getCode() == KeyCode.ESCAPE) tmpStage.close();
				});
				break;

			case SaveView:
				tmpController = new SaveController();
				tmpView = SaveView.getInstance();
				if(tmpView == null) return;
				tmpScene = new Scene((SaveView)tmpView);
				tmpStage.setTitle("Save...");
				tmpController.link(model, tmpView);
				break;

			case LoadView:
				tmpController = new LoadController();
				tmpView = LoadView.getInstance();
				if(tmpView == null) return;
				tmpScene = new Scene((LoadView)tmpView);
				tmpStage.setTitle("Load...");
				tmpController.link(model, tmpView);
				break;
		}
		tmpStage.setScene(tmpScene);
		tmpStage.setResizable(false);
		tmpStage.showAndWait();
		tmpView.destroy();
	}
}
