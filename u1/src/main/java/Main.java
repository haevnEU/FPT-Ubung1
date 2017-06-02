import javafx.application.*;

import core.Model;
import view.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class provides start up routines
 */
public final class Main extends Application {

    /**
     * Entry point for Application
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Model model;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            System.out.println("Application start routine");
            System.out.println("Loading data");

            model = new Model();

            MainController mainController = new MainController();
            MainView view = new MainView();
            view.init();

            mainController.link(model, view);

            Scene s = new Scene(view,750,500);
            s.setOnKeyPressed(e -> onKeyPressed(e));
            primaryStage.setResizable(false);
            primaryStage.setScene(s);
            primaryStage.setOnCloseRequest(e->System.out.println("Application closed"));
            primaryStage.show();

            primaryStage.setOnCloseRequest(e -> Platform.exit());

        }
        catch(Exception ex){
            System.err.println("Start routine failed!");
            ex.printStackTrace();
            Platform.exit();
        }
    }

    /**
     * Handles key pressing
     * @param e Key event argument
     */
    private void onKeyPressed(KeyEvent e) {
        if(e.isAltDown() && e.getCode() == KeyCode.L) model.loadDirectory();
        else if(e.isAltDown() && e.getCode() == KeyCode.ESCAPE) Platform.exit();
    }
}