import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class View extends BorderPane{

    private ListView lvPlayList, lvQueue;


    private GridPane bottomControl;
    private Label lbTitle, lbRemain;
    private ToggleButton btPlayPause;
    private ProgressBar pbProgress;

    private MenuBar menuBar;
    private Menu menuGeneral, menuHelp;
    private MenuItem menuItemOpenDetails, menuItemOpenFile, menuItemClose, menuItemAbout;

    public View(){

        prepareBottomControl();
        setBottom(bottomControl);

        prepareMenu();
        setTop(menuBar);

        lvPlayList = new ListView();
        lvPlayList.setMaxWidth(200);
        lvPlayList.setMinWidth(200);
        lvPlayList.getItems().add("Zum laden ctrl + L");
        lvPlayList.getItems().add("oder im Menu Load auswählen");
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
        menuItemOpenFile = new MenuItem("Open File");
        menuItemClose = new MenuItem("Close Application");
        menuGeneral = new Menu("General");
        menuGeneral.getItems().addAll(menuItemOpenFile, menuItemOpenDetails, menuItemClose);

        menuItemAbout = new MenuItem("About");
        menuHelp = new Menu("Help");
        menuHelp.getItems().addAll(menuItemAbout);

        menuBar.getMenus().addAll(menuGeneral, menuHelp);
    }

    }
