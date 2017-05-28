package controller;

import javafx.scene.image.Image;
import view.*;
import core.*;
import interfaces.*;
import javafx.scene.*;

import javafx.stage.Stage;
import java.util.Observable;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

/**
 * this class provides main view logic
 */
public class MainController implements interfaces.IController{
    private Model model;
    private MainView view;

    @Override
    public void link(IModel model, IView view) {
        this.model = (Model)model;
        this.view = (MainView)view;

        this.view.addButtonDeleteCLickEventHandler(e -> buttonAllSongsClick(e));
        this.view.addButtonPlayPauseClickEventHandler(e -> buttonPlayPauseClick(e));
        this.view.addButtonSkipClickEventHandler(e -> buttonSkipClick(e));

        this.view.bindButtonDeleteText(this.model.getLocale().deleteProperty());
        this.view.bindButtonPlayPauseText(this.model.getLocale().playProperty());
        this.view.bindButtonSkipText(this.model.getLocale().skipProperty());


        this.view.bindListViewQueue(this.model.getQueue());
        this.view.addListViewQueueClickedEventHandler(e -> listViewClicked(e));
        this.model.addObserver(this);
    }

    /**
     * Handles listView clicked event
     * @param e event params
     */
    private void listViewClicked(MouseEvent e) {
        if(model.getQueue().size() <= 0) return;
        Song selectedSong = (Song)view.getSelectedQueueItem();

        if(e.getClickCount() == 2) model.setNextSong(selectedSong);
        if(e.isSecondaryButtonDown() && e.getClickCount() == 2) invokeDetailWindow(selectedSong);
        if(e.isAltDown() && e.getClickCount() >= 2)model.getQueue().remove(selectedSong);
    }

    /**
     * Invokes detail window for selected song
     * @param selectedSong song which should show the details
     */
    private void invokeDetailWindow(Song selectedSong) {
        DetailView detailView = new DetailView(selectedSong);
        detailView.init();

        DetailController detailController = new DetailController();
        detailController.link(model, detailView);
       showStage(detailView, WindowState.show, selectedSong.titleProperty().getValue());
    }

    /**
     * Invokes the window which shows all loaded songs
     */
    private void invokeAllSongsWindow(){
        AllSongsView songsView = AllSongsView.getInstance();
        if(songsView == null) return;
        songsView.init();

        AllSongsController songsController = new AllSongsController();
        songsController.link(model, songsView);

        showStage(songsView, WindowState.showAndWait, "All songs");

        if(model.getQueue().size() <= 0) return;
        model.setNextSong((Song)model.getQueue().get(0));
        model.setFirstSong();
        this.view.bindImageCover(model.getNextSong().getCover());

        model.addTimeChangeListener((observable, oldValue, newValue) ->  this.view.update(newValue.toSeconds()));
        model.addOnReady(() -> onReady());

        this.view.bindLabelAlbumText(this.model.getNextSong().albumProperty());
        this.view.bindLabelInterpretText(this.model.getNextSong().interpretProperty());
        this.view.bindLabelTitleText(this.model.getNextSong().titleProperty());
    }

    private void onReady() {
        System.out.println(this.model.getLength());
    }

    /**
     * Event handling for button click
     * @param e Action event details
     */
    private void buttonAllSongsClick(ActionEvent e) {
      invokeAllSongsWindow();
    }

    /**
     * Shows a stage
     * @param view view which implement the IView interface
     * @param state represented by State enum possible states: show and showAndWait
     * @param title window title
     */
    private void showStage(IView view, WindowState state, String title){
        Scene s = new Scene((Parent) view);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(s);
        stage.setOnCloseRequest(e -> view.destroy() );
        if(WindowState.show == state) stage.show();
        if(WindowState.showAndWait == state) {
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        }
    }

    /**
     * Event handling for button click
     * @param e Action event details
     */
    private void buttonPlayPauseClick(ActionEvent e){
        if(model.getQueue().size() <= 0) return;

        model.stateProperty().set(!model.stateProperty().getValue());
        if(model.stateProperty().getValue()) {
            model.play();
            model.getLocale().playProperty().set("Pause");
        }
        else {
            model.pause();
            model.getLocale().playProperty().set("Play");
        }
    }
    /**
     * Event handling for button click
     * @param e Action event details
     */
    private void buttonSkipClick(ActionEvent e){
        if(model.getQueue().size() <= 1){
            buttonPlayPauseClick(null);
        }
        model.skip();

        model.addOnReady(() -> onReady());
        this.view.bindLabelAlbumText(this.model.getNextSong().albumProperty());
        this.view.bindLabelInterpretText(this.model.getNextSong().interpretProperty());
        this.view.bindLabelTitleText(this.model.getNextSong().titleProperty());
    }

    @Override
    public void update(Observable o, Object arg) {
        view.bindImageCover(model.getNextSong().getCover());
        //view.setSliderSeekMax(model.getSeekLength());
    }

}