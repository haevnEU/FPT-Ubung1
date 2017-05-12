import core.InvalidFileException;
import core.Model;
import detailView.DetailView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    private Model model;
    private View view;



    public void link(Model m, View v){

        this.model = m;

        this.view = v;
        this.view.addMenuItemLoadEventHandler(e -> menutItemLoadEventHandler(e));
        this.view.addMenuItemAboutEventHandler(e -> menuItemAboutEventHandler(e));
        this.view.addMenuItemDetailEventHandler(e -> menuItemDetailEventHandler(e));

        this.view.addButtonPlayPauseEventHandler(e -> playPause(e));
    }


    public void menuItemDetailEventHandler(javafx.event.ActionEvent e) {
        DetailView m = new DetailView();
        detailView.Controller detailController = new detailView.Controller();

        detailController.link(model, m);
        Stage menuStage = new Stage();
        Scene s = new Scene(m);
        s.setOnKeyPressed(e2 -> { if(e2.getCode() == KeyCode.ESCAPE) menuStage.close(); });
        menuStage.setTitle("Details");
        menuStage.setScene(s);
        menuStage.setResizable(false);
        menuStage.show();

    }


    public void menutItemLoadEventHandler(javafx.event.ActionEvent e) {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory =
                    directoryChooser.showDialog(Main.getPrimaryStage());


            this.model.load(selectedDirectory.getAbsolutePath());
            Main.getPrimaryStage().setTitle("Test");

        }
        catch(Exception ex){
            core.util.showExceptionMessage(ex);
        }
    }

    public void menuItemAboutEventHandler(javafx.event.ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Musicplayer indev 00.1");
        alert.setContentText("Keybindings \n" +
                "alt + F1 : Help menu\n" +
                "alt + F4 : Close Application\n" +
                "alt + L  : Load Directory\n" +
                "alt + D  : Open Song Details\n" +
                "Space    : Toggle Play / Pause");

        alert.showAndWait();
    }

    void playPause(javafx.event.ActionEvent e){
        // Toggle play pause
        view.togglePlayPause();
        // pause player

    }
}
