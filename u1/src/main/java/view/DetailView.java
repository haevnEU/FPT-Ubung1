package view;

import javafx.scene.control.*;

import core.Song;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class provides a detailed view represented by a songList which must be the queue
 */
public class DetailView extends GridPane implements interfaces.IView {

    Song song;
    public DetailView(Song s){
        this.song = s;
        init();
    }

    @Override
    public void destroy() {}

    @Override
    public void init() {
        setPadding(new Insets(10));

        setHgap(10);
        setVgap(10);

        Label lbTitle, lbAlbum, lbInterpret, lbID, lbPath, lbYear;
        TextField tbTitle, tbAlbum, tbInterpret, tbID, tbPath, tbYear;

        lbTitle = new Label("Title: ");
        lbAlbum = new Label("Album: ");
        lbInterpret = new Label("Artist: ");
        lbID = new Label("ID: ");
        lbPath = new Label("Path: ");
        lbYear = new Label("Year: ");

        // Note with the usage of simple properties it is possible to declare the control local and not global
        // this is possible of the usage from bindBiDirectional
        // a bidirectional binding allows a lack of submit button => design decision
        tbTitle = new TextField();
        tbTitle.textProperty().bindBidirectional(song.titleProperty());

        tbAlbum = new TextField();
        tbAlbum.textProperty().bindBidirectional(song.albumProperty());

        tbInterpret = new TextField();
        tbInterpret.textProperty().bindBidirectional(song.interpretProperty());

        tbID = new TextField();
        tbID.textProperty().bind(song.idProperty().asString());

        tbPath = new TextField();
        tbPath.textProperty().bindBidirectional(song.pathProperty());

        tbYear = new TextField();

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

        // prepare layout
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

       getChildren().addAll(lbAlbum, lbID, lbInterpret, lbPath, lbTitle, lbYear, tbTitle, tbAlbum, tbInterpret, tbID, tbPath, tbYear);
    }
}
