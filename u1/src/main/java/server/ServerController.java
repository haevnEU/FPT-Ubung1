package server;

import core.*;
import view.*;
import java.rmi.*;
import interfaces.*;
import javafx.scene.input.*;
import javafx.scene.control.*;

import java.util.List;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

/**
 * This class provides server functionality
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
public final class ServerController implements interfaces.IController {
	private ServerView view;
	private Model model;
	private Thread loginThread, UDPServer;
	private ServerController(){}

	private List<IModel> clients;
	public ServerController(List<IModel> clients){
		this();
		this.clients = clients;
	}
	@Override
	public void link(IModel m, IView v) {
		this.model = (Model) m;
		this.view = (ServerView) v;

		view.addButtonExitClicked(this::btExitClicked);
		view.addButtonRestartClicked(this::btRestartClicked);
		view.addButtonStartClicked(this::btStartClicked);
		view.addButtonLoadClicked(this::btLoadClicked);
		view.addButtonSaveClicked(this::btSaveClicked);
		view.addListViewLibraryClicked(this::listViewLibraryClicked);
		view.addListViewQueueClicked(this::listVIewQueueClicked);
		view.addTextFieldConsoleKeyDown(this::textFieldConsoleKeyDown);
		view.bindListViews(model.getQueue(), model.getLibrary());
		view.addOnQueueChange(this::updateQueue);
	}

	private void updateQueue(ListChangeListener.Change<? extends ISong> change) {

		Platform.runLater(()->{try {
			model.updateQueueView(new RawList(model.getQueue()));
		} catch (RemoteException e) {
			e.printStackTrace();
		}});
	}

	private void textFieldConsoleKeyDown(KeyEvent keyEvent) {
		if(keyEvent.getCode() == KeyCode.ENTER){
			CVars.setStaticVariable(view.getConsoleText());
			view.resetConsole();
		}
	}

	private void listVIewQueueClicked(MouseEvent e) {
		if(e.isAltDown() && e.getClickCount() == 2) model.getQueue().remove(view.getSelectedQueueSong());
		else if(e.getClickCount() == 2) Util.invokeNewWindow(SceneType.DetailView, null, view.getSelectedQueueSong());
	}

	private void listViewLibraryClicked(MouseEvent e) {
		if(e.getClickCount() == 2 && e.isAltDown()) Util.invokeNewWindow(SceneType.DetailView, null, view.getSelectedLibrarySong());
		else if( e.getClickCount() == 2) model.getQueue().add(view.getSelectedLibrarySong());
	}

	private void btRestartClicked(MouseEvent e) {
		System.out.println("[DEV NOTE]Not implemented yet!");
		if(null != loginThread && null != UDPServer){
			// Rebind
			try{
				Naming.rebind("IModel", model);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// Reset server ?!
		}
	}

	private void btStartClicked(MouseEvent e) {
		try {
			// Start up routine:
			// 1. Media player
			// 2. RMI
			// 3. TCP/UDP Server
			// If the server started first, the ports will be blocked to avoid this we must use this order

			// 1.
			model.callPlayerInit(model.getQueue());

			// 2.
			Naming.rebind("IModel", model);

			System.out.println("[INFO] Successfully created remote model");

			// 3.
			// The TCP and UDP server are daemons
			//      that mean they have a low priority and they are killed with quiting the application
			loginThread = new Thread(new TCPServer(model, clients), "Login-Server");
			loginThread.setDaemon(true);
			loginThread.start();

			UDPServer = new Thread(new UDPServer(model), "State-Server");
			UDPServer.setDaemon(true);
			UDPServer.start();


			view.setButtonStartDisable(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("[SYS CRIT] Server initialize failed! Server is not running well. Exception message: " +ex.getMessage());
		}
	}

	private void btExitClicked(MouseEvent e) {

		System.out.println("Restart Clicked");
		Alert warning = new Alert(Alert.AlertType.WARNING,
				"Are you sure to quit the Server?" +
						"\n\nAll connected clients are going to be disconnected" +
						"\n\nQueue and Library will rollback if they are not saved.",
				ButtonType.YES, ButtonType.CANCEL);
		warning.showAndWait();
		if(warning.getResult() == ButtonType.YES){
			// TODO Log expected shutdown
			Platform.exit();
			System.exit(0);
		}
	}

	private void btLoadClicked(MouseEvent e) {
		Util.invokeNewWindow(SceneType.LoadView, model);
	}

	private void btSaveClicked(MouseEvent e) {
		Util.invokeNewWindow(SceneType.SaveView, model);
	}


}
