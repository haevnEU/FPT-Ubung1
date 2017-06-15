package controller;

import ApplicationException.RichException;
import javafx.scene.layout.BorderPane;
import view.*;
import core.*;
import interfaces.*;
import javafx.stage.*;
import javafx.scene.input.*;

import javafx.scene.Scene;
import java.rmi.RemoteException;
import javafx.beans.property.SimpleBooleanProperty;

public class MainController implements interfaces.IController{


    private MainView view;
	private Model model;
	@Override
    public void link(IModel m, IView v){

        this.model = (Model)m;
        this.view = (MainView)v;

        this.view.addLoadFilesClickEventHandler(e -> menuItemLoadEventHandler());
        this.view.addDetailClickEventHandler(e -> menuItemDetailEventHandler());
        this.view.DeleteClickEventHandler(e -> menuItemDeleteEventHandle());


        this.view.addButtonAllEventHandler(e -> addAll());

        this.view.addButtonPlayPauseEventHandler(e -> playPause());
        this.view.addButtonSkipEventHandler(e-> skipSong());
		this.view.addButtonSaveEventHandler( e-> invokeNewWindow(SceneType.SaveView));
        this.view.addListViewAllSongClickEventHandler((e) -> listViewAllSongClicked(e));

        this.view.setAllSongs(model.getAllSongs());
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
       if(e.isAltDown() && e.getClickCount() == 2) invokeNewWindow(SceneType.DetailView);
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
    private void menuItemLoadEventHandler() {
		invokeNewWindow(SceneType.LoadView);
    }

    /**
     * EventHandler for DetailView
     */
    private void menuItemDetailEventHandler() {
    	invokeNewWindow(SceneType.DetailView);
	 }

    /**
     * EventHandler for opening DeleteView
     */
    private void menuItemDeleteEventHandle() {
        invokeNewWindow(SceneType.DeleteView);
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
        	new RichException(ex);
        }
    }

	/**
	 * Skips current song
	 */
	private  void skipSong(){ model.skip(); }


	/**
	 * Invokes any window
	 * @param t which scene should be invoked, details could be seen inside view.SceneType
	 */
	private void invokeNewWindow(SceneType t){

		Scene tmpScene = new Scene(new BorderPane());
		Stage tmpStage = new Stage();
		IController tmpController;
		IView tmpView = new EmptyView();
		switch(t){
			case DeleteView:
				tmpController = new DeleteController();
				tmpView = DeleteView.getInstance(model.getQueue());
				if(tmpView == null) return;
				tmpScene = new Scene((DeleteView)tmpView);

				tmpController.link(model, tmpView);

				tmpStage.setWidth(250);
				tmpScene.setOnKeyPressed(e -> tmpStage.close());
				break;

			case DetailView:
				Song s = this.view.getSelectedSong();
				tmpController = new DetailController();
				tmpView = new DetailView();
				tmpScene = new Scene((DetailView)tmpView);

				tmpController.link(model, tmpView);
				((DetailController)tmpController).init(s);

				tmpStage.setTitle(s.getTitle());
				tmpScene.setOnKeyPressed(e -> tmpStage.close());
				break;

			case SaveView:
				tmpController = new SaveController();
				tmpView = SaveView.getInstance();
				if(tmpView == null) return;
				tmpScene = new Scene((SaveView)tmpView);

				tmpController.link(model, tmpView);
				break;

			case LoadView:
				tmpController = new LoadController();
				tmpView = LoadView.getInstance();
				if(tmpView == null) return;
				tmpScene = new Scene((LoadView)tmpView);

				tmpController.link(model, tmpView);
				break;
		}
		tmpStage.setScene(tmpScene);
		tmpStage.setResizable(false);
		tmpStage.showAndWait();
		tmpView.destroy();
	}
}
