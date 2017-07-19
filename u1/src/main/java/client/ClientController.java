package client;

import core.*;
import java.net.*;
import interfaces.*;

import view.SceneType;
import java.rmi.Naming;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.RemoteException;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class provides the fundamental client logic
 * <p>
 * Created by Nils Milewsi (nimile) on 15.07.17
 */
public class ClientController implements interfaces.IController {

	private Stage viewStage;
	private IModel model, stub;
	private ClientView view;
	private SimpleBooleanProperty updateQueue = new SimpleBooleanProperty(false);

	// Forbid the default ctor
	private ClientController(){}

	public ClientController(Stage viewStage) {
		this();
		this.viewStage = viewStage;
	}

	/**
	 * Links a model and a view with this controller
	 * @param model Model which should be used to bound
	 * @param view View which should be used to bound
	 */
	public void link(IModel model, IView view) {
		this.view = (ClientView)view;

		// NOTE this way is used to determine the enabling of network functionality from the view
		this.view.addOnRMIInitHandler((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->{
			this.view.addButtonPlayPauseClickedEventhandler(this::btPlayPauseClicked);
			this.view.addButtonSkipClickedEventhandler(this::btSkipClicked);
			this.view.addButtonStopClickedEventhandler(this::btStopClicked);
			this.stub = this.view.getStub();
			this.model = Model.getInstance();
			try {
				this.view.setItems(((Model) this.model).getQueue());
				Naming.rebind(this.view.getName(), model);

			} catch (RemoteException e) {
				System.out.println();
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			this.view.updateBtTogglePlayPauseText();
			this.view.addListViewLibraryCliecked(this::listViewLibraryClicked);
			updateQueue = ((Model)model).getQueueUpdate();
			updateQueue.addListener((observable1, oldValue1, newValue1) -> {
				System.out.println(((Model) model).getQueue().size());
				this.view.setItems(((Model) model).getQueue());
				updateQueue.setValue(false);
			});
			// NOTE Determine that the queue has change
			// Start time request
			Thread runner = new Thread(this::getTime);
			runner.setDaemon(true);
			runner.start();
		});
	}

	private void listViewLibraryClicked(MouseEvent mouseEvent) {
		if( mouseEvent.isAltDown() && mouseEvent.getClickCount() >= 2){
			SongWrapper selectedSong = view.getSelectedLibrarySong();
			Song song = new Song(selectedSong.getId());
			song.setAlbum(selectedSong.getAlbum());
			song.setTitle(selectedSong.getTitle());
			song.setInterpret(selectedSong.getArtist());
			song.setPath(selectedSong.getPath());
			Util.invokeNewWindow(SceneType.DetailView, null, song);

		}
		else if(mouseEvent.getClickCount() == 2){
			try {
				stub.addToQueue(view.getSelectedLibrarySong());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	private void btStopClicked(MouseEvent mouseEvent) {
		try {
			stub.stop();
			view.updateBtTogglePlayPauseText();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void btSkipClicked(MouseEvent mouseEvent) {
		try {
			stub.skip();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void btPlayPauseClicked(MouseEvent mouseEvent) {
		try {
			stub.togglePlayPause();
			view.updateBtTogglePlayPauseText();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void getTime() {
		while (true) {
			try {
				String cmd = "{CMD:TIME}";
				byte[] sendData = cmd.getBytes();
				byte[] receiveData = new byte["{RESULT:ERROR}".length()];
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName(CVars.getServerIp());

				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, CVars.getTimePort());
				clientSocket.send(sendPacket);
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.setSoTimeout(10);
				clientSocket.receive(receivePacket);
				String modifiedSentence = "";
				for (byte b : receivePacket.getData()) {
					for (int i = '0'; i <= '9'; i++) {
						if (b == i) modifiedSentence += (char) b;
					}
				}
				Long time = Long.parseLong(modifiedSentence);
				int min = (int) (time / 60);
				int sec = (int) (time % 60);
				Platform.runLater(() -> viewStage.setTitle(String.format("%02d", min) + ":" + String.format("%02d", sec)));
				clientSocket.close();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
