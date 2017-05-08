import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // hier die Daten verwalten
        Model model = new Model();

        View view = new View();

        /////Verschiedene Lösungsmöglichkeiten für den Controller (nur eine wird benötigt) /////

        //Lösung mit Lambda-Ausdruck Controller
        //ControllerWithLambda controllerWithLambda = new ControllerWithLambda();
        //controllerWithLambda.link(model, view);

        //Lösung ohne Lambda-Ausdruck im Controller
        //ControllerWithoutLambda controllerWithoutLamdba = new ControllerWithoutLambda();
        //controllerWithoutLamdba.link(model, view);

        //Lösung durch die Implemementierung des Interfaces durch den Controller
        //ControllerImplementsInterface controllerImplementsInterface = new ControllerImplementsInterface();
        //controllerImplementsInterface.link(model, view);

        // JavaFX new
        Scene scene = new Scene(view);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
}