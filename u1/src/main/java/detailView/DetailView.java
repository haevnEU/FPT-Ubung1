package detailView;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DetailView extends VBox {

    private Label lbTitle, lbInterpret, lbAlbum, lbBase;
    private TextField tbTitle, tbInterpret, tbAlbum;
    private Button btCommit;
    private HBox hbTitle, hbInterpret, hbAlbum;

    public DetailView(){

        prepareDetails();

        getChildren().addAll(lbBase,hbTitle, hbInterpret, hbAlbum, btCommit);
        setPadding(new Insets(10, 10, 10, 10));
    }

    private void prepareDetails(){
        lbTitle = new Label("Titel: ");
        lbTitle.setPrefWidth(100);
        tbTitle = new TextField();
        hbTitle = new HBox();
        hbTitle.getChildren().addAll(lbTitle, tbTitle);

        lbInterpret = new Label("Interpret: ");
        lbInterpret.setPrefWidth(100);
        tbInterpret = new TextField();
        hbInterpret = new HBox();
        hbInterpret.getChildren().addAll(lbInterpret, tbInterpret);

        lbAlbum = new Label("Album: ");
        lbAlbum.setPrefWidth(100);
        tbAlbum = new TextField();
        hbAlbum = new HBox();
        hbAlbum.getChildren().addAll(lbAlbum, tbAlbum);

        lbBase = new Label("ID und Länge ");
        lbBase.setPrefWidth(100);

        btCommit = new Button("Ändern");
        setSpacing(10);
    }


    public void addButtonCommitEventHandler(EventHandler<ActionEvent> eventHandler) {
        btCommit.addEventHandler(ActionEvent.ACTION, eventHandler);
    }



}
