import core.controller.Controller;
import core.util.Model;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    // following lines are used to access primaryStage inside other classes
    private static Stage stage;
	public static Stage getPrimaryStage(){ return stage;}

    @Override
    public void start(Stage primaryStage) throws Exception {

        Model model = Model.getInstance();
        core.view.View mainView = new core.view.View();
	    Controller mainController = new Controller();
        mainController.link(model, mainView);

        Scene scene = new Scene(mainView);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.show();
        stage = primaryStage;
    }
}