package view;

import ApplicationException.DatabaseException;
import core.LoginCredentials;
import core.SelectedSongList;
import core.Util;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaveView extends BorderPane implements interfaces.IView {

	private static SaveView instance;

	private CheckBox cbEnableDB;
	private TextField tbUsername;
	private PasswordField pwUsername;
	private Button btXml, btBin, btDB, btOpenJPA;
	private Label lbUserName, lbPW;
	RadioButton rbplayList, rbQueue;
	ToggleGroup toggleGroup;

	public static SaveView getInstance() {
		if(instance != null) return null;

		instance = new SaveView();
		return instance;
	}

	private SaveView(){
		HBox box1 = new HBox();
		HBox box2 = new HBox();
		HBox box3 = new HBox();
		VBox center = new VBox();
		VBox left = new VBox();

		tbUsername = new TextField();
		tbUsername.setDisable(true);
		lbUserName = new Label("Username: ");
		lbUserName.setDisable(true);

		pwUsername = new PasswordField();
		pwUsername.setDisable(true);
		lbPW = new Label("Password: ");
		lbPW.setDisable(true);

		box1.getChildren().addAll(lbUserName, tbUsername);
		box2.getChildren().addAll(lbPW, pwUsername);


		toggleGroup = new ToggleGroup();
		rbplayList = new RadioButton(SelectedSongList.Library.name());
		rbplayList.setId(SelectedSongList.Library.toString());
		rbQueue = new RadioButton(SelectedSongList.Playlist.name());
		rbQueue.setId(SelectedSongList.Playlist.toString());
		rbQueue.setSelected(true);
		rbplayList.setToggleGroup(toggleGroup);
		rbQueue.setToggleGroup(toggleGroup);
		box3.getChildren().addAll(rbplayList,rbQueue);

		box1.setSpacing(10);
		box2.setSpacing(10);
		box3.setSpacing(10);
		center.setPadding(new Insets(10));
		left.setPadding(new Insets(10));

		center.setSpacing(10);
		left.setSpacing(10);

		cbEnableDB = new CheckBox("Enable Database functionality");

		center.getChildren().addAll(box3, box1, box2, cbEnableDB);

		btXml = new Button("XML");
		btXml.setPrefWidth(150);

		btBin = new Button("Binary");
		btBin.setPrefWidth(150);

		btDB = new Button("DB");
		btDB.setPrefWidth(150);
		btDB.setDisable(true);

		btOpenJPA = new Button("JPA");
		btOpenJPA.setPrefWidth(150);
		btOpenJPA.setDisable(true);

		left.getChildren().addAll(btXml, btBin, btDB, btOpenJPA);

		setLeft(left);
		setCenter(center);
		setPadding(new Insets(10));
	}


	public void addCheckBoxDbEnableEventHandler(EventHandler<ActionEvent> eventHandler) {
		cbEnableDB.addEventHandler(ActionEvent.ACTION, eventHandler);
	}

	/**
	 * Adds event handling for bt xml click
	 * @param eventEventHandler Event which should be fired
	 */
	public void addBtXmlClickEventHandler(EventHandler<ActionEvent> eventEventHandler){
		btXml.addEventHandler(ActionEvent.ACTION, eventEventHandler);
	}

	/**
	 * Adds event handling for bt binary click
	 * @param eventEventHandler Event which should be fired
	 */
	public void addBtBinClickEventHandler(EventHandler<ActionEvent> eventEventHandler){
		btBin.addEventHandler(ActionEvent.ACTION, eventEventHandler);
	}

	/**
	 * Adds event handling for bt db click
	 * @param eventEventHandler Event which should be fired
	 */
	public void addBtDbClickEventHandler(EventHandler<ActionEvent> eventEventHandler){
		btDB.addEventHandler(ActionEvent.ACTION, eventEventHandler);
	}

	/**
	 * Adds event handling for bt openJPA click
	 * @param eventEventHandler Event which should be fired
	 */
	public void addBtOpenJPAClickEventHandler(EventHandler<ActionEvent> eventEventHandler){
		btOpenJPA.addEventHandler(ActionEvent.ACTION, eventEventHandler);
	}

	/**
	 * Get the login credentials for DB access
	 * @return
	 */
	public LoginCredentials getLogin(){
		if(!cbEnableDB.isSelected()) return null;
		String username, pw;

		username = tbUsername.getText();
		pw = pwUsername.getText();

		try {
			return new LoginCredentials(username, pw);
		} catch (DatabaseException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * Adds event handling for toggle group
	 * @param e method which should be invoked
	 */
	public void addToggleSongList(ChangeListener<Toggle> e){
		toggleGroup.selectedToggleProperty().addListener(e);
	}

	public void toggleDbView(){
		tbUsername.setDisable(!tbUsername.disabledProperty().getValue());
		lbUserName.setDisable(!lbUserName.disabledProperty().getValue());

		pwUsername.setDisable(!pwUsername.disabledProperty().getValue());
		lbPW.setDisable(!lbPW.disabledProperty().getValue());

		btDB.setDisable(!btDB.disabledProperty().getValue());
		btOpenJPA.setDisable(!btOpenJPA.disabledProperty().getValue());
	}

	@Override
	public void destroy() {
		instance = null;
	}

	/**
	 * closes this view
	 */
	public void close() {
		((Stage) this.getScene().getWindow()).close();
	}
}
