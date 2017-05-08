import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class View extends BorderPane{

    private boolean playPause = true;

    private ListView lvPlayList, lvQueue;


    private GridPane bottomControl;
    private Label lbTitle, lbRemain;
    private ToggleButton btPlayPause;
    private ProgressBar pbProgress;

    private MenuBar menuBar;
    private Menu menuGeneral, menuHelp;
    private MenuItem menuItemOpenDetails, menuItemOpenFile, menuItemClose, menuItemAbout;

    // Unsichtbare
    private MenuItem menuItemPlayPause;

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
        lbTitle = new Label("Hier steht der titel");
        lbRemain = new Label("Zeit");
        btPlayPause = new ToggleButton("Play");

        pbProgress = new ProgressBar();

        bottomControl = new GridPane();
        bottomControl.add(lbTitle,0,0);
        bottomControl.add(lbRemain,2,0);
        bottomControl.add(pbProgress,1,0);
        bottomControl.add(btPlayPause,3,0);

    }

    private void prepareMenu(){

        menuBar = new MenuBar();

        menuItemOpenDetails = new MenuItem("Details");
        menuItemOpenDetails.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN));

        menuItemOpenFile = new MenuItem("Open File");
        menuItemOpenFile.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN));
        menuItemClose = new MenuItem("Close Application");
        menuItemClose.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        menuGeneral = new Menu("General");
        menuGeneral.getItems().addAll(menuItemOpenFile, menuItemOpenDetails, menuItemClose);


        menuItemAbout = new MenuItem("About");
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1, KeyCombination.ALT_DOWN));
        menuHelp = new Menu("Help");
        menuHelp.getItems().addAll(menuItemAbout);


        menuBar.getMenus().addAll(menuGeneral, menuHelp);



        // Unsichtbare Menus
        menuItemPlayPause = new MenuItem();
        menuItemPlayPause.setAccelerator(new KeyCodeCombination(KeyCode.SPACE));

    }


    public void addButtonPlayPauseEventHandler(EventHandler<ActionEvent> eventHandler) {
        btPlayPause.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addMenutItemLoadEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemOpenFile.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addMenutItemCloseEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemClose.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void addmenuItemAboutEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemAbout.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    // Unsichtbare Menu

    public void addmenuItemPlayPauseEventHandler(EventHandler<ActionEvent> eventHandler) {
        menuItemPlayPause.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void togglePlayPause() {
        if(playPause) btPlayPause.setText("Pause");
        else btPlayPause.setText("Play");
        playPause = !playPause;
    }
}
