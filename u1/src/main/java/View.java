import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.util.Map;

/**
 * This class provides our mainwindow
 */
public class View extends BorderPane implements interfaces.IView{

//     state of mediaplayer
    private boolean playPause = true;

//     UI Stuff
    private ListView lvPlayList, lvQueue;

    private VBox bottomControl;
    private Label lbRemain;
    private ToggleButton btPlayPause;
    private Slider sliderProgress;

    private MenuBar menuBar;
    private Menu menuGeneral, menuHelp;
    private MenuItem menuItemOpenDetails, menuItemOpenFile, menuItemOpenDelete, menuItemAbout;

//    ctor
    public View(){

        prepareBottomControl();
        setBottom(bottomControl);

        prepareMenu();
        setTop(menuBar);

        lvPlayList = new ListView();
        lvPlayList.setMaxWidth(200);
        lvPlayList.setMinWidth(200);
        setLeft(lvPlayList);

        lvQueue = new ListView();
        lvQueue.getItems().add("Noch nichts vorhanden :(");
        setCenter(lvQueue);
    }

    /**
     * Prepares the bottom control, e.g. Play, position, etc !INTERNAL USAGE!
     */
    private void prepareBottomControl(){

        lbRemain = new Label("Zeit");
        btPlayPause = new ToggleButton("Play");


        sliderProgress = new Slider();

        bottomControl = new VBox();
        bottomControl.setPadding(new Insets(10));
        bottomControl.getChildren().add(lbRemain);
        bottomControl.getChildren().add(sliderProgress);
        bottomControl.getChildren().add(btPlayPause);

    }

    /**
     * Prepares the menu !INTERNAL USAGE!
     */
    private void prepareMenu(){

        menuBar = new MenuBar();

        menuItemOpenDetails = new MenuItem("Details");
        menuItemOpenDetails.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        menuItemOpenFile = new MenuItem("Open File");
        menuItemOpenFile.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
        menuItemOpenDelete = new MenuItem("Open Delete");
        menuItemOpenDelete.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        menuGeneral = new Menu("General");
        menuGeneral.getItems().addAll(menuItemOpenFile, menuItemOpenDetails, menuItemOpenDelete);


        menuItemAbout = new MenuItem("About");
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1, KeyCombination.CONTROL_DOWN));
        menuHelp = new Menu("Help");
        menuHelp.getItems().addAll(menuItemAbout);


        menuBar.getMenus().addAll(menuGeneral, menuHelp);

    }

//    NOTE Im note describe the following methods because they just attach events to every object
    public void addMenuItemOpenDeleteEventHandler(EventHandler<ActionEvent> eventHandler ){
        menuItemOpenDelete.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addMenuItemDetailEventHandler(EventHandler<ActionEvent> eventHandler ){
        menuItemOpenDetails.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addButtonPlayPauseEventHandler(EventHandler<ActionEvent> eventHandler) {
        btPlayPause.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addMenuItemLoadEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemOpenFile.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addMenuItemAboutEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemAbout.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void togglePlayPause() {
        if(playPause) btPlayPause.setText("Pause");
        else btPlayPause.setText("Play");
        playPause = !playPause;
    }

//    From this point Im start again with describing

    @Override
    public void setLocale(Map<String, String> locale) {

//        TODO LOGIC
    }
}
