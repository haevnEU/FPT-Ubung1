
import client.ClientController;
import client.ClientView;
import core.*;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IModel;
import javafx.scene.input.*;
import javafx.application.*;

import server.ServerController;
import server.ServerView;
import sun.jvm.hotspot.debugger.cdbg.CVAttributes;
import view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainController;
import view.SceneType;

import javax.print.attribute.standard.MediaSize;

public class Main extends Application {


	private static SceneType sceneType = SceneType.MainView;
	/**
	 * Entry point
	 * @param args console arguments
	 */
    public static void main(String[] args) {

    	// Parameter
	    if(args.length > 0){
	    	// Iterate over each parameter
	    	for(String s : args){
	    		// Redirect Critical exception
			    if(s.toUpperCase().contains("-RCRIT:")){
				    try {
					    String file = s.substring(s.indexOf(":") + 1, s.length());
					    File f = new File(file);
					    if (!f.exists()) f.createNewFile();
					    FileOutputStream fos = new FileOutputStream(f);
					    System.setErr(new PrintStream(fos));
				    }catch (IOException ex){}
			    }
			    // Redirect warnings
			    else if(s.toUpperCase().contains("-RWARN:")){
				    try {
					    String file = s.substring(s.indexOf(":") + 1, s.length());
					    File f = new File(file);
					    if (!f.exists()) f.createNewFile();
					    FileOutputStream fos = new FileOutputStream(f);
					    System.setOut(new PrintStream(fos));
				    }catch (IOException ex){}
			    }
			    // Redirect warnings and critical exception
			    else if(s.toUpperCase().contains("-R:")){
				    try {
					    String file = s.substring(s.indexOf(":") + 1, s.length());
					    File f = new File(file);
					    if (!f.exists()) f.createNewFile();
					    FileOutputStream fos = new FileOutputStream(f);
					    System.setErr(new PrintStream(fos));
					    System.setOut(new PrintStream(fos));
				    }catch (IOException ex){}
			    }
			    // enables own db functionality
			    else if(s.toUpperCase().contains("-OWNDBPATH")){
			    	Model.setCustomDBFeature(true);
			    }
			    else if(s.toUpperCase().equals("-SERVER")){
			    	sceneType = SceneType.ServerView;
			    }
			    else if(s.toUpperCase().equals("-CLIENT")){
			        sceneType = SceneType.ClientView;
			    }
		    }
	    }

	    System.out.println("[INFO] Application started at " + Util.getUnixTimeStamp());
	    Application.launch(args);
    }


	private List<IModel> clients = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

	    Model model = Model.getInstance(clients);
	    Scene scene = null;

		switch (sceneType){
			case MainView:
				MainView mainView = new MainView();
				MainController mainController = new MainController();
				mainController.link(model, mainView);

				scene = new Scene(mainView);
				break;

			case ServerView:
				ServerView serverView = new ServerView();
				ServerController serverController = new ServerController(clients);
				serverController.link(model, serverView);

				primaryStage.setResizable(false);
				scene = new Scene(serverView);
				CVars.setRMIName("IModel");
				break;

			case ClientView:
				ClientView clientView = new ClientView(primaryStage);
				ClientController clientController = new ClientController(primaryStage);
				clientController.link(model, clientView);

				primaryStage.setResizable(false);
				scene = new Scene(clientView);
				CVars.setIsClientEnabled();
				break;
		}
        if(null == scene) onClose();
	    primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> onClose());

	    scene.setOnKeyPressed(this::sceneOnKeyDown);
	}

    private void sceneOnKeyDown(KeyEvent e) {
        if(e.isAltDown() && e.getCode() == KeyCode.ESCAPE) Platform.exit();
	}

    private void onClose() {
	    System.out.println("[INFO] Application quited at " + Util.getUnixTimeStamp());

	    try{
		    for(String s : Naming.list("localhost")){
			    try {
			    	if(s.contains(CVars.getRMIName())) {
					    Naming.unbind(s);
					    System.out.println("[INFO] Unbind: " + s);
				    }
			    } catch (RemoteException e) {
				    e.printStackTrace();

			    } catch (NotBoundException e) {
				    e.printStackTrace();
			    } catch (MalformedURLException e) {
				    e.printStackTrace();
			    }
		    }
	    } catch (MalformedURLException e) {
		    e.printStackTrace();
	    } catch (RemoteException e) {
		    e.printStackTrace();
	    }
	    Platform.exit();
	    System.exit(0);
    }
}

