package core.view;

import core.util.Song;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DetailView extends VBox implements core.interfaces.IView{

    private Song s;
    // I<3UI
    private TextField tbTitle, tbInterpret, tbAlbum;
    private Button btCommit;


    // modified singleton pattern
    // there only one instanced allowed and usable per time
    private static int numInstances = 0;
    private static DetailView v;
    public static DetailView getInstance(Song s){
        // if the instance is not null and the counter counts more than 0 instances we must deny new instances
        if(v != null && numInstances > 0) return null;
        numInstances++;
        v = new DetailView(s);
        return v;
    }
    public void closeView(){
        v = null;
        numInstances = 0;
    }
    // normal constructor just modified for singleton usage
    private DetailView(Song s){
        this.s = s;
        initUI();
    }


    // Used to prepare !INTERNAL USAGE!
    private void initUI(){
        Label lbTitle, lbInterpret, lbAlbum, lbBase;
        HBox hbTitle, hbInterpret, hbAlbum;

        lbTitle = new Label("Title: ");
        lbTitle.setPrefWidth(100);
        tbTitle = new TextField();
        tbTitle.setText(s.getTitle());
        hbTitle = new HBox();
        hbTitle.getChildren().addAll(lbTitle, tbTitle);

        lbInterpret = new Label("Interpret: ");
        lbInterpret.setPrefWidth(100);
        tbInterpret = new TextField();
        tbInterpret.setText(s.getInterpret());
        hbInterpret = new HBox();
        hbInterpret.getChildren().addAll(lbInterpret, tbInterpret);

        lbAlbum = new Label("Album: ");
        lbAlbum.setPrefWidth(100);
        tbAlbum = new TextField();
        tbAlbum.setText(s.getAlbum());
        hbAlbum = new HBox();
        hbAlbum.getChildren().addAll(lbAlbum, tbAlbum);

        lbBase = new Label("ID: " + s.getId());
        lbBase.setPrefWidth(100);

        btCommit = new Button("Change");

        setSpacing(10);
        setPadding(new Insets(10));

        getChildren().addAll(lbBase,hbTitle, hbInterpret, hbAlbum, btCommit);
    }


    /**
     * Adds an event handler to commit button
      * @param eventHandler event handler for button click events
     */
    public void addButtonCommitEventHandler(EventHandler<ActionEvent> eventHandler) {
        btCommit.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public String getTitle(){ return tbTitle.getText(); }

    public String getInterpret(){ return tbInterpret.getText(); }

    public String getAlbumName(){ return tbAlbum.getText(); }

}