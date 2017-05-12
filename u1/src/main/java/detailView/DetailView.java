package detailView;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DetailView extends VBox {


    // modified singleton pattern
    // there only one instanced allowed and usable per time
    private static int numInstances = 0;
    private static DetailView v;
    public static DetailView getInstance(){
        // if the instance is not null and the counter counts more than 0 instances we must deny new instances
        if(v != null && numInstances > 0) return null;
        numInstances++;
        v = new DetailView();
        return v;
    }
    // this method is used to reset the class
    public static void closeView(){
        // set instance to zero => destroying object
        v = null;
        // reset counter
        numInstances = 0;
    }
    // normal ctor just modified for singleton usage
    private DetailView(){

        prepareDetails();

        getChildren().addAll(lbBase,hbTitle, hbInterpret, hbAlbum, btCommit);
        setPadding(new Insets(10));
    }

    // I<3UI
    private Label lbTitle, lbInterpret, lbAlbum, lbBase;
    private TextField tbTitle, tbInterpret, tbAlbum;
    private Button btCommit;
    private HBox hbTitle, hbInterpret, hbAlbum;

    // Used to prepare !INTERNAL USAGE!
    private void prepareDetails(){
        // TODO Change fixed string with localization
        lbTitle = new Label("Titel: ");
        lbTitle.setPrefWidth(100);
        tbTitle = new TextField();
        hbTitle = new HBox();
        hbTitle.getChildren().addAll(lbTitle, tbTitle);

//        TODO Change fixed string with localization
        lbInterpret = new Label("Interpret: ");
        lbInterpret.setPrefWidth(100);
        tbInterpret = new TextField();
        hbInterpret = new HBox();
        hbInterpret.getChildren().addAll(lbInterpret, tbInterpret);

//        TODO Change fixed string with localization
        lbAlbum = new Label("Album: ");
        lbAlbum.setPrefWidth(100);
        tbAlbum = new TextField();
        hbAlbum = new HBox();
        hbAlbum.getChildren().addAll(lbAlbum, tbAlbum);

//        TODO Change fixed string with localization
        lbBase = new Label("ID und Länge ");
        lbBase.setPrefWidth(100);

//        TODO Change fixed string with localization
        btCommit = new Button("Ändern");

//        Set UI spacing to 10pixel on each side
        setSpacing(10);
    }


    /**
     * Adds an eventhandler to commit button
      * @param eventHandler
     */
    public void addButtonCommitEventHandler(EventHandler<ActionEvent> eventHandler) {
        btCommit.addEventHandler(ActionEvent.ACTION, eventHandler);
    }



}
