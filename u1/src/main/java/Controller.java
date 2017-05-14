import core.Model;
import deleteView.DeleteController;
import deleteView.DeleteView;
import detailView.DetailController;
import detailView.DetailView;
import interfaces.IView;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Here were are the mainview! Im happy!
/**
 * This class is used as a main controller
 */
public class Controller extends interfaces.IController{

    private Model model;
    private View view;

    private Map<String, String> locale = new HashMap<>();

    public void link(Model m, IView v){

        this.model = m;

        this.view = (View)v;
        this.view.addMenuItemLoadEventHandler(e -> menuItemLoadEventHandler(e));
        this.view.addMenuItemDetailEventHandler(e -> menuItemDetailEventHandler(e));
        this.view.addMenuItemOpenDeleteEventHandler(e -> menuItemDeleteEventHandle(e));

        this.view.addMenuItemAboutEventHandler(e -> menuItemAboutEventHandler(e));

        this.view.addButtonPlayPauseEventHandler(e -> playPause(e));

        locale = core.util.load(getClass().getName());

    }




    /**
     * Loads any directory
     * @param e
     */
    public void menuItemLoadEventHandler(javafx.event.ActionEvent e) {
        try {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files","*.mp3"));
            List<File> selectedFiles = fc.showOpenMultipleDialog(null);
            if (selectedFiles != null)
                view.setLvPlayList(selectedFiles);
        }
        catch(Exception ex){
            core.util.showExceptionMessage(ex);
        }


        /* try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(Main.getPrimaryStage());

            this.model.load(selectedDirectory.getAbsolutePath());
            Main.getPrimaryStage().setTitle(selectedDirectory.getName());
        }
        catch(Exception ex){
            core.util.showExceptionMessage(ex);
        } */
    }


    /**
     * Eventhandler for DetailView
     * @param e
     */
    public void menuItemDetailEventHandler(javafx.event.ActionEvent e) {

        // In this method im not describe singleton, look at the method above
        DetailView m =DetailView.getInstance();
        if(m == null) return;
        double centerX = Main.getPrimaryStage().getX() + (Main.getPrimaryStage().getWidth() * 0.5);
        double centerY = Main.getPrimaryStage().getY() + (Main.getPrimaryStage().getHeight() * 0.5);
        DetailController detailController = new DetailController();
        detailController.link(model, m);
        Stage menuStage = new Stage();
        Scene s = new Scene(m);
        menuStage.setWidth(250);
        menuStage.setX(centerX - (menuStage.getWidth() * 0.5));
        menuStage.setY(centerY - (menuStage.getHeight() * 0.5));
        menuStage.setTitle("Details");
        menuStage.setScene(s);
        menuStage.setResizable(false);
        menuStage.setAlwaysOnTop(true);
        // Close with escape key, only difference
        s.setOnKeyPressed((KeyEvent e2) -> {
            if(e2.getCode() == KeyCode.ESCAPE) {
                menuStage.close();
                DetailView.closeView();
            }
        });
        menuStage.setOnCloseRequest(e3-> DetailView.closeView ());
        menuStage.show();
    }

    /**
     * Eventhandler for opening Deleteview
     * @param e
     */
    public void menuItemDeleteEventHandle(ActionEvent e){
        // Singleton part II access to the object!
        // here we receive the object from the class, !IMPORTANT! class not object
        deleteView.DeleteView v = DeleteView.getInstance();
        // return if view is null
        if(v == null) return;
        deleteView.DeleteController deleteController = new DeleteController();
        deleteController.link(model,v);

        // calculate position on screen
        double centerX = Main.getPrimaryStage().getX() + (Main.getPrimaryStage().getWidth() * 0.5);
        double centerY = Main.getPrimaryStage().getY() + (Main.getPrimaryStage().getHeight() * 0.5);


        Stage deleteStage = new Stage();

        deleteStage.setHeight(Main.getPrimaryStage().getHeight() * 0.9);
        deleteStage.setWidth(250);
        deleteStage.setX(centerX + (deleteStage.getWidth()));
        deleteStage.setY(centerY - (deleteStage.getHeight() * 0.5));
        deleteStage.setResizable(false);
        deleteStage.setTitle("Delete");

        // Singleton part II reset object... if we miss this call we cannot create new objects from this view
        deleteStage.setOnCloseRequest(e3-> deleteView.DeleteView.closeView ());

        Scene s = new Scene(v);
        deleteStage.setScene(s);
        deleteStage.show();

    }


    /**
     * Opens About window
     * @param e
     */
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

    /**
     * This method toggles play and pause
     * @param e
     */
    void playPause(javafx.event.ActionEvent e){
        // Toggle play pause
        view.togglePlayPause();
        // pause player

    }

}
