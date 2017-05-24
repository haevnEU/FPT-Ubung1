package core.view;

import core.util.Song;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class provides our MainWindow
 */
public class View extends BorderPane implements core.interfaces.IView{

	// Updates with every new song, contains the length of song
	private String preTitle = "NaN";
    private ListView lvAllSongs;
	private ListView<core.interfaces.Song> lvQueue;
    private ToggleButton btPlayPause;
    private Button btNext, btAddAll;
	private Button btOpenDetails, btOpenFile, btOpenDelete;
	private Slider sliderCurrentTime;

    public View() {

    	prepareBottomControl();
        prepareSideMenu();

        lvAllSongs = new ListView();
        lvAllSongs.setMaxWidth(200);
        lvAllSongs.setTooltip(new Tooltip("Double click adds to queue"));
	    lvQueue = new ListView<>();

	    VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(10));
        leftBox.setSpacing(10);
        leftBox.getChildren().addAll(new Label("All Songs"),lvAllSongs);
        setCenter(leftBox);

        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(10));
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(new Label("Current queue"), lvQueue);
        setRight(centerBox);
     }

	/**
     * Prepares the bottom control, e.g. Play, position, etc !INTERNAL USAGE!
     */
    private void prepareBottomControl(){

    	sliderCurrentTime = new Slider();

        btPlayPause = new ToggleButton("\u25B6");
	    btPlayPause.setTooltip(new Tooltip("Toggles play pause"));
		btPlayPause.setPrefSize(40,40);

        btNext = new Button("\u00BB");
	    btNext.setTooltip(new Tooltip("Skips current track"));
		btNext.setPrefSize(40,40);

	    Label lbName = new Label();
		VBox verticalControl = new VBox();
		verticalControl.setSpacing(10);
		verticalControl.setPadding(new Insets(0,10,0,10));

		HBox controls = new HBox();
	    controls.setPadding(new Insets(0,0,10,0)); // Space to each side
        controls.setSpacing(10);    // space between each element
	    controls.getChildren().addAll(btPlayPause, btNext, lbName); // adding
        controls.setAlignment(Pos.BASELINE_CENTER); // center elements

	    verticalControl.getChildren().addAll(sliderCurrentTime,controls);

	    setBottom(verticalControl);
    }

	/**
	 * Prepares the side menu
	 */
	private void prepareSideMenu(){

	    btOpenDetails = new Button("\u270E");
	    btOpenDetails.setPrefSize(40,40);
	    btOpenDetails.setTooltip(new Tooltip("Open Details"));

	    btOpenFile = new Button("\u23CD");
	    btOpenFile.setPrefSize(40,40);
	    btOpenFile.setTooltip(new Tooltip("Open File"));

	    btOpenDelete = new Button("\u20E0");//2672
	    btOpenDelete.setPrefSize(40,40);
	    btOpenDelete.setTooltip(new Tooltip("Open Delete"));

		btAddAll = new Button("\u002B");
		btAddAll.setPrefSize(40,40);
		btAddAll.setTooltip(new Tooltip("Adds every song to queue"));


	    VBox test = new VBox();
	    test.setPadding(new Insets(10));
	    test.setSpacing(10);
	    test.getChildren().addAll(new Label("Menu"), btOpenFile, btOpenDetails, btOpenDelete, btAddAll);
	    setLeft(test);
    }

	/**
	 * Set the AllSong listView
	 * @param allSongs SongsList which represent all songs
	 */
	public void setAllSongs (core.util.SongList allSongs){ lvAllSongs.setItems(allSongs); }

	/**
	 * Set the queueView
	 * @param queue SongList which represent the queue
	 */
	public void setQueue(core.util.SongList queue){
        lvQueue.setItems(queue);
    }

	/**
	 * Toggles play and pause
	 * @param playPause current state of media player
	 */
	public void togglePlayPause(SimpleBooleanProperty playPause) {
		btPlayPause.setSelected(playPause.getValue());
		if(btPlayPause.isSelected()) btPlayPause.setText("II");
		else btPlayPause.setText("\u25B6");
	}

	/**
	 * Get the current selected song from AllSong listView
	 * @return zero based index
	 */
	public Song getSelectedSong() { return (Song) lvAllSongs.getSelectionModel().getSelectedItem(); }

	/**
	 * Get the current selected song from Queue listView
	 * @return zero based index
	 */
	public int getSelectedQueueIndex(){ return lvQueue.getSelectionModel().getSelectedIndex(); }

	/**
	 * Updates the current time
	 * @param time time inside the song
	 */
	public void updateTime(Duration time) {
		sliderCurrentTime.setValue(time.toSeconds());

		String out = "" + String.format("%02d", (int)(time.toMinutes())) + ":" + String.format("%02d", ((int)(time.toSeconds()) - (60*(int)time.toMinutes())));

		// Show song length and current time in window title, bsp. 00:01 total: 03:00
		((Stage)(sliderCurrentTime.getScene().getWindow())).setTitle(out + " total: " + preTitle);
	}

	/**
	 * Set the new maximum of the slider
	 * @param songLength new maximum => Song length as seconds
	 */
	public void setSongLength(double songLength) {
		System.out.println(songLength);
		this.sliderCurrentTime.setMax(songLength);
		int min, sec;
		min = (int)(songLength / 60);
		sec = (int) (songLength - (min * 60));
		preTitle = min + ":" + sec;
		System.out.println(preTitle);
	}




	// From here we just add event handler

	public void addButtonAllEventHandler(EventHandler<ActionEvent> eventHandler){
		btAddAll.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public void addButtonSkipEventHandler(EventHandler<ActionEvent> eventHandler){
		btNext.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public void addButtonPlayPauseEventHandler(EventHandler<ActionEvent> eventHandler) {
		btPlayPause.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public void DeleteClickEventHandler(EventHandler<ActionEvent> eventHandler ){
		btOpenDelete.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public  void addDetailClickEventHandler(EventHandler<ActionEvent> eventHandler ){
		btOpenDetails.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public void addLoadFilesClickEventHandler(EventHandler<ActionEvent> eventHandler) {
		btOpenFile.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	public void addListViewAllSongClickEventHandler(EventHandler<MouseEvent> eventHandler) {
		lvAllSongs.setOnMouseClicked(eventHandler);
	}
}
