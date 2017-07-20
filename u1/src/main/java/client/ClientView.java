package client;

import core.*;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;
import interfaces.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.property.SimpleBooleanProperty;


/**
 * This class provides the UI for the client
 * <p>
 * Created by Nils Milewsi (nimile) on 15.07.17
 */
public class ClientView extends VBox implements interfaces.IView {

	private IModel stub;
	private String name;
	private Stage viewStage;
	private boolean expanded = false;
	private Button btStop, btSkip, btPlayPause;
	private ListView<ISong> lvLibrary, lvQueue;
	private SimpleBooleanProperty rmiInitialized = new SimpleBooleanProperty(false);

	public String getName() {
		return name;
	}

	private ClientView(){}

	/**
	 * Creates a new window
	 * @param viewStage stage which the window content should appears
	 */
	public ClientView(Stage viewStage) {
		this();

		viewStage.setTitle("Mglrmglmglmgl.");
		TextField tbUsername = new TextField();
		tbUsername.setPrefWidth(150);
		Label lbUsername = new Label("Username: ");
		lbUsername.setPrefWidth(150);
		HBox hBox = new HBox(lbUsername, tbUsername);
		hBox.setSpacing(10);
		PasswordField pwPw = new PasswordField();
		pwPw.setPrefWidth(150);
		Label lbPW = new Label("Password: ");
		lbPW.setPrefWidth(150);
		HBox hBox2 = new HBox(lbPW, pwPw);
		hBox2.setSpacing(10);
		Button btLogin = new Button("Login");
		btLogin.setPrefWidth(100);
		Label lbError = new Label();
		HBox bottom = new HBox(btLogin, lbError);
		bottom.setSpacing(10);
		bottom.setPrefHeight(50);

		pwPw.setOnKeyPressed((e)->{ if(e.getCode() == KeyCode.ENTER)init(tbUsername, pwPw, lbError); });
		btLogin.setOnKeyPressed((e) ->{ if(e.getCode() == KeyCode.ENTER)init(tbUsername, pwPw, lbError); });
		btLogin.setOnMouseClicked((e)-> init(tbUsername, pwPw, lbError));

		getChildren().addAll(hBox, hBox2, bottom);
		setPadding(new Insets(10));
		setSpacing(10);
		this.viewStage = viewStage;
	}

	private  void init(TextField tbUsername, PasswordField pwPw, Label lbError){
		if(login(tbUsername.getText(), pwPw.getText(), lbError)){
			name = tbUsername.getText();
			CVars.setRMIName(name);
			initUI();
			initRMI();
			try {
				lvLibrary.setItems(stub.getRawLibrary().getSongList());
				lvQueue.setItems(stub.getRawQueue().getSongList());
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
		}
	}

	private boolean login(String username, String password, Label lbError) {
		int result = Integer.MIN_VALUE;
		try (Socket serverCon = new Socket(CVars.getServerIp(), CVars.getLoginPort());
		     InputStream in = serverCon.getInputStream();
		     OutputStream out = serverCon.getOutputStream()) {
			byte[] buffer = (username +"-" + password + ";").getBytes();
			out.write(buffer);
			out.flush();

			result = in.read();
		} catch (IOException e) {
			lbError.setText("Possible connection problems?");
			e.printStackTrace();
		}
		if(result > 0){
			lbError.setText("Something went terrible wrong!");

			List<String> texte = new ArrayList<>();
			texte.add("Something's not quite right...");
			texte.add("Who goes there?!");
			texte.add("An illusion! What are you hiding?");
			Random rnd = new Random();

			Alert alertBox = new Alert(Alert.AlertType.ERROR, texte.get(rnd.nextInt(texte.size()*10 + 1) % texte.size())+"\nSomething seems suspicious with your data...", ButtonType.OK);
			alertBox.setTitle("Aaaaaughibbrgubugbugrguburgle!");
			alertBox.showAndWait();
		}
		return result == 0;
	}

	private void initRMI() {
		try {
			stub = (IModel) Naming.lookup("IModel");
			rmiInitialized.setValue(true);
		} catch (RemoteException | NotBoundException | MalformedURLException ex) {
			ex.printStackTrace();
		}
	}

	private void initUI(){
		getChildren().clear();
		btStop = new Button("STOP");
		btStop.setPrefWidth(150);
		btPlayPause = new Button("Play");
		btPlayPause.setPrefWidth(150);
		btSkip = new Button("SKIP");
		btSkip.setPrefWidth(150);
		HBox buttonBox = new HBox(btStop, btPlayPause, btSkip);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		buttonBox.setPadding(new Insets(10));

		Label expander = new Label("\u21D5");
		expander.setOnMouseClicked(this::expand);
		expander.setFont(Font.font(16));
		setAlignment(Pos.CENTER);
		lvLibrary = new ListView<>();
		lvLibrary.setVisible(false);
		lvQueue = new ListView<>();
		lvQueue.setVisible(false);

		HBox listBox = new HBox(new VBox(new Label("Library"), lvLibrary), new VBox(new Label("Queue"), lvQueue));
		listBox.setSpacing(10);
		getChildren().clear();
		getChildren().add(buttonBox);
		getChildren().add(expander);
		getChildren().add(listBox);
		viewStage.setHeight(viewStage.getHeight() - 17);
	}

	public void addButtonStopClickedEventhandler(EventHandler<MouseEvent> eventHandler){
		btStop.setOnMouseClicked(eventHandler);
	}

	public void addButtonSkipClickedEventhandler(EventHandler<MouseEvent> eventHandler){
		btSkip.setOnMouseClicked(eventHandler);
	}

	public void addButtonPlayPauseClickedEventhandler(EventHandler<MouseEvent> eventHandler){
		btPlayPause.setOnMouseClicked(eventHandler);
	}

	public void addListViewLibraryCliecked(EventHandler<MouseEvent> eventHandler){
		lvLibrary.setOnMouseClicked(eventHandler);
	}

	public void addListViewQueueCliecked(EventHandler<MouseEvent> eventHandler){
		lvQueue.setOnMouseClicked(eventHandler);
	}

	public void addOnRMIInitHandler(ChangeListener<Boolean> event){
		rmiInitialized.addListener(event);
	}

	public void updateBtTogglePlayPauseText(boolean b) {
		if (b) btPlayPause.setText("Pause");
		else btPlayPause.setText("Play");
	}

	private void expand(MouseEvent e) {
		if(expanded){
			expanded = !expanded;
			viewStage.setHeight(viewStage.getHeight() - 300);
		}else{
			expanded = !expanded;
			viewStage.setHeight(viewStage.getHeight() + 300);
		}

		lvLibrary.setVisible(expanded);
		lvQueue.setVisible(expanded);
	}

	/**
	 * Returns the stub
	 * @return stub from RMI
	 */
	IModel getStub() {
		return stub;
	}

	/**
	 * Returns the selected library song
	 * @return Library song
	 */
	SongWrapper getSelectedLibrarySong() {
		return new SongWrapper(lvLibrary.getSelectionModel().getSelectedItem());
	}

	/**
	 * Set queue items
	 * @param list List which should be used to set
	 */
	void setItems(SongList list){
		Platform.runLater(() -> {
			lvQueue.getItems().clear();
			for(ISong s : list) {
				lvQueue.getItems().add(s);
			}
		});
	}

	/**
	 * Should reset the object
	 */
	@Override
	public void destroy() {}
}
