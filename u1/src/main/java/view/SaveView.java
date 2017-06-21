package view;

import core.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import interfaces.IView;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.beans.value.ChangeListener;
import ApplicationException.DatabaseException;

public class SaveView extends BorderPane implements IView {

	private static SaveView instance;

	private CheckBox cbEnableDB;
	private TextField tbUsername;
	private PasswordField pwUsername;
	private Button btXml, btBin, btDB, btOpenJPA;
	private Label lbUserName, lbPW;
	private RadioButton rbLibrary, rbQueue;
	private ToggleGroup toggleGroup;

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
		rbLibrary = new RadioButton(SelectedSongList.Library.name());
		rbLibrary.setId(SelectedSongList.Library.toString());
		rbLibrary.setTooltip(new Tooltip("Saving Library"));
		rbQueue = new RadioButton(SelectedSongList.PlayList.name());
		rbQueue.setId(SelectedSongList.PlayList.toString());

		rbQueue.setTooltip(new Tooltip("Saving Queue"));

		rbLibrary.setSelected(true);
		rbLibrary.setToggleGroup(toggleGroup);
		rbQueue.setToggleGroup(toggleGroup);
		box3.getChildren().addAll(rbLibrary,rbQueue);

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
		btXml.setTooltip(new Tooltip("Save playlist or queue metainformation as XML file"));

		btBin = new Button("Binary");
		btBin.setPrefWidth(150);
		btBin.setTooltip(new Tooltip("Save playlist or queue metainformation as binary file"));

		btDB = new Button("DB");
		btDB.setPrefWidth(150);
		btDB.setDisable(true);
		btDB.setTooltip(new Tooltip("Save playlist or queue metainformation isnide a database"));

		btOpenJPA = new Button("JPA");
		btOpenJPA.setPrefWidth(150);
		btOpenJPA.setDisable(true);
		btOpenJPA.setTooltip(new Tooltip("Save playlist or queue metainformation isnide a database using JPA"));

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
	 * @return entered login credentials
	 */
	public LoginCredentials getLogin(){
		if(!cbEnableDB.isSelected()) return null;
		String username, pw;

		username = tbUsername.getText();
		pw = pwUsername.getText();

		try {
			return new LoginCredentials(username, pw);
		} catch (DatabaseException ex) {
			System.err.println("[CRIT] Database exception occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
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
