package server;

import core.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import interfaces.ISong;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.collections.ListChangeListener;


/**
 * This class provides server view
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
public class ServerView extends VBox implements interfaces.IView {

	private TextArea tbLog;
	private Button btStart, btRestart, btExit, btLoad, btSave;
	private ListView<ISong> lvLibrary, lvQueue;
	private TextField tbConsole;
	/**
	 * Constructor for ServerView
	 */
	public ServerView() {

		HBox hBox = new HBox();
		VBox vBox1, vBox2, vBox3;
		vBox1 = new VBox();
		btStart = new Button("Start");
		btStart.setPrefWidth(150);

		btRestart = new Button("Restart");
		btRestart.setPrefWidth(150);

		btExit = new Button("Exit");
		btExit.setPrefWidth(150);

		btLoad = new Button("Load");
		btLoad.setPrefWidth(150);

		btSave = new Button("Save");
		btSave.setPrefWidth(150);

		vBox1.setSpacing(10);
		vBox1.setPadding(new Insets(10));
		vBox1.setMaxWidth(100);
		vBox1.setMinWidth(100);
		vBox1.getChildren().addAll(btStart, btRestart, btExit, btLoad, btSave);

		vBox2 = new VBox();
		lvLibrary = new ListView<>();
		VBox.setVgrow(lvLibrary, Priority.ALWAYS);
		HBox.setHgrow(vBox2, Priority.ALWAYS);
		vBox2.setMinWidth(230);
		vBox2.getChildren().addAll(new Label("Library"), lvLibrary);

		vBox3 = new VBox();
		lvQueue = new ListView<>();
		VBox.setVgrow(lvQueue, Priority.ALWAYS);
		HBox.setHgrow(vBox3, Priority.ALWAYS);
		vBox3.setMinWidth(230);
		vBox3.getChildren().addAll(new Label("Queue"), lvQueue);

		hBox.setSpacing(10);
		hBox.setPadding(new Insets(10, 0, 10, 10));
		hBox.getChildren().addAll(vBox1, vBox2, vBox3);

		tbLog = new TextArea();
		tbLog.setText("Console version beta 1.0\n" +
				"Hier sollte eigentlich die Ausgabe vom server umgeleitet werden.\n" +
				"CVars usage (Requires a restart)\n" +
				"to modify a CVars you have to enter the CVar name followed by a : and the value into the box above\n" +
				"Following CVars are able to modify\n" +
				"LOGINPORT:<PORT> Modify the port which the server accept login request\n" +
				"TIMEPORT:<PORT> Modify the port which the server accept time get request\n" +
				"RMIPORT:<PORT> Specify the RMI Registry\n" +
				"SERVERIP:<IP> Change the server IP, note you can user name (localhost) or blank IP (127.0.0.1)\n" +
				"Replace <...> with a specific value" +
				"In addition you can type EXIT to kill the server application");
		tbLog.setEditable(false);
		tbConsole = new TextField();
		minWidth(600);
		setPadding(new Insets(10));
		getChildren().addAll(hBox, tbConsole,tbLog);

		tbLog.textProperty().addListener((observable, oldValue, newValue) -> tbLog.setScrollTop(Double.MAX_VALUE));
	}

	void addButtonStartClicked(EventHandler<MouseEvent> eventHandler) {
		btStart.setOnMouseClicked(eventHandler);
	}

	void addButtonRestartClicked(EventHandler<MouseEvent> eventHandler) {
		btRestart.setOnMouseClicked(eventHandler);
	}

	void addButtonExitClicked(EventHandler<MouseEvent> eventHandler) {
		btExit.setOnMouseClicked(eventHandler);
	}

	void addButtonLoadClicked(EventHandler<MouseEvent> eventHandler) {
		btLoad.setOnMouseClicked(eventHandler);
	}

	void addButtonSaveClicked(EventHandler<MouseEvent> eventHandler) {
		btSave.setOnMouseClicked(eventHandler);
	}

	void addListViewQueueClicked(EventHandler<MouseEvent> eventHandler) {
		lvQueue.setOnMouseClicked(eventHandler);
	}

	void addListViewLibraryClicked(EventHandler<MouseEvent> eventHandler) {
		lvLibrary.setOnMouseClicked(eventHandler);
	}

	void addTextFieldConsoleKeyDown(EventHandler<KeyEvent> event){
		tbConsole.setOnKeyPressed(event);
	}

	void addOnQueueChange(ListChangeListener<ISong> c){
		lvQueue.getItems().addListener(c);
	}

	String getConsoleText(){ return tbConsole.getText(); }

	void resetConsole(){
		tbConsole.setText("");
	}

	/**
	 * Should reset the object
	 */
	@Override
	public void destroy() {}

	void setButtonStartDisable(boolean buttonStartDisable) {
		btStart.setDisable(buttonStartDisable);
	}

	void bindListViews(SongList queue, SongList library) {
		lvLibrary.setItems(library);
		lvQueue.setItems(queue);
	}

	Song getSelectedQueueSong() {
		return (Song) lvQueue.getSelectionModel().getSelectedItem();
	}

	Song getSelectedLibrarySong() {
		return (Song) lvLibrary.getSelectionModel().getSelectedItem();
	}
}
