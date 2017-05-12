import core.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by nilsmilewski on 05.05.17.
 */
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

        Controller mainController = new Controller();
        View mainView = new View();
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