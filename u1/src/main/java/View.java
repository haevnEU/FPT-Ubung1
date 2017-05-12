import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class View extends BorderPane{



    private boolean playPause = true;

    // UI Stuff
    private ListView lvPlayList, lvQueue;

    private VBox bottomControl;
    private Label lbRemain;
    private ToggleButton btPlayPause;
    private Slider sliderProgress;

    private MenuBar menuBar;
    private Menu menuGeneral, menuHelp;
    private MenuItem menuItemOpenDetails, menuItemOpenFile, menuItemAbout;


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

    private void prepareBottomControl(){
        lbRemain = new Label("Zeit");
        btPlayPause = new ToggleButton("Play");


        sliderProgress = new Slider();

        bottomControl = new VBox();

        bottomControl.getChildren().add(lbRemain);//,2,0);
        bottomControl.getChildren().add(sliderProgress);//,1,0);
        bottomControl.getChildren().add(btPlayPause);//,3,0);

    }

    private void prepareMenu(){

        menuBar = new MenuBar();

        menuItemOpenDetails = new MenuItem("Details");
        menuItemOpenDetails.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN));

        menuItemOpenFile = new MenuItem("Open File");
        menuItemOpenFile.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN));
        menuGeneral = new Menu("General");
        menuGeneral.getItems().addAll(menuItemOpenFile, menuItemOpenDetails);


        menuItemAbout = new MenuItem("About");
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1, KeyCombination.ALT_DOWN));
        menuHelp = new Menu("Help");
        menuHelp.getItems().addAll(menuItemAbout);


        menuBar.getMenus().addAll(menuGeneral, menuHelp);

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
}
