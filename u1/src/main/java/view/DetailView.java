package view;

import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import core.Song;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;

/**
 * This class provides UI for detail action
 *
 * Written by Nils Milewski (nimile)
 */
public class DetailView extends GridPane implements interfaces.IView{

    private TextField tbTitle;
    private TextField tbInterpret;
    private TextField tbAlbum;

    private Button btCommit;

    // Used to prepare !INTERNAL USAGE!
    public void initUI(Song song){
        setPadding(new Insets(10));

        setHgap(10);
        setVgap(10);

        Label lbTitle = new Label("Title: ");
        Label lbAlbum = new Label("Album: ");
        Label lbInterpret = new Label("Artist: ");
        Label lbID = new Label("ID: ");
        Label lbPath = new Label("Path: ");
        Label lbYear = new Label("Year: ");

        tbTitle = new TextField();
        // alt tbTitle.setText(song.getTitleProperty().getValue());
        // or tbTitle.textProperty.bindBiDirectional(song.getTitleProperty());
        tbTitle.setText(song.getTitle());

        tbAlbum = new TextField();
        tbAlbum.setText(song.getAlbum());

        tbInterpret = new TextField();
        tbInterpret.setText(song.getInterpret());

        TextField tbID = new TextField();
        tbID.setText(song.getId()+"");
        tbID.editableProperty().setValue(false);

        TextField tbPath = new TextField();
        tbPath.setText(song.getPath());

        TextField tbYear = new TextField();

        // Check if the ISong has a cover, if not we skip this part
        // => this part is enclosed from the rest
        if(song.getCover() != null) {
            ImageView cover;
            cover = new ImageView();
            cover.setImage(song.getCover());

            setColumnIndex(cover, 2);
            setRowIndex(cover, 0);
            setRowSpan(cover, 7);
            getChildren().add(cover);
        }

        btCommit = new Button("Change");

        // prepare layout => you should skip this... its a huge tone of code which positions UI Controls
        setColumnIndex(lbTitle,0);
        setColumnIndex(lbAlbum,0);
        setColumnIndex(lbInterpret,0);
        setColumnIndex(lbID,0);
        setColumnIndex(lbPath,0);
        setColumnIndex(lbYear,0);

        setColumnIndex(tbTitle,1);
        setColumnIndex(tbAlbum,1);
        setColumnIndex(tbInterpret,1);
        setColumnIndex(tbID,1);
        setColumnIndex(tbPath,1);
        setColumnIndex(tbYear,1);

        setRowIndex(lbTitle,0);
        setRowIndex(lbAlbum,1);
        setRowIndex(lbInterpret,2);
        setRowIndex(lbID,3);
        setRowIndex(lbPath,4);
        setRowIndex(lbYear,5);

        setRowIndex(tbTitle,0);
        setRowIndex(tbAlbum,1);
        setRowIndex(tbInterpret,2);
        setRowIndex(tbID,3);
        setRowIndex(tbPath,4);
        setRowIndex(tbYear,5);


        setRowIndex(btCommit,8 );
        setColumnIndex(btCommit,0);
        setColumnSpan(btCommit,2);

        getChildren().addAll(lbAlbum, lbID, lbInterpret, lbPath, lbTitle, lbYear, tbTitle, tbAlbum, tbInterpret, tbID, tbPath, tbYear, btCommit);
    }

    /**
     * Adds an event handler to commit button
      * @param eventHandler event handler for button click events
     */
    public void addButtonCommitEventHandler(EventHandler<ActionEvent> eventHandler) {
        btCommit.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    /**
     * Inside this TextBox should be the song title
     * @return song title
     */
    public String getTitle(){ return tbTitle.getText(); }

    /**
     * Inside this TextBox should be the song interpret
     * @return song interpret
     */
    public String getInterpret(){ return tbInterpret.getText(); }


    /**
     * Inside this TextBox should be the song album
     * @return song album
     */
    public String getAlbumName(){ return tbAlbum.getText(); }

    @Override
    public void destroy() {}
}