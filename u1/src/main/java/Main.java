
import controller.SaveController;
import javafx.scene.input.*;
import javafx.application.*;

import core.Model;
import view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainController;
import view.SaveView;

import java.util.ArrayList;

public class Main extends Application {

	/**
	 * Entry point
	 * @param args console arguments
	 */
    public static void main(String[] args) {
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
	    Platform.exit();
	    System.exit(0);
    }
}