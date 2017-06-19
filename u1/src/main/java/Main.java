
import core.Util;
import javafx.scene.input.*;
import javafx.application.*;

import core.Model;
import view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainController;

import java.io.*;

public class Main extends Application {

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
			    else if(s.toUpperCase().contains("-ENABLEFEATURE")){
			    	Model.setCustomDBFeature(true);
			    }
		    }
	    }

	    System.out.println("[NONE] Application started at " + Util.getUnixTimeStamp());
	    Application.launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Model model = Model.getInstance();

        MainView mainView = new MainView();
	    MainController mainController = new MainController();
        mainController.link(model, mainView);

        Scene scene = new Scene(mainView);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> onClose());
        scene.setOnKeyPressed(e -> sceneOnKeyDown(e));
	}

    private void sceneOnKeyDown(KeyEvent e) {
        if(e.isAltDown() && e.getCode() == KeyCode.ESCAPE)Platform.exit();
	}

    private void onClose() {
	    System.out.println("[NONE] Application quited at " + Util.getUnixTimeStamp());
	    Platform.exit();
	    System.exit(0);
    }
}