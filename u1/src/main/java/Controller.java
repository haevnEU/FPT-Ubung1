import core.InvalidFileException;
import javafx.scene.control.Alert;

import static com.sun.javafx.application.PlatformImpl.exit;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Controller {

    private Model model;
    private View view;


    public void link(Model m, View v){

        this.model = m;

        this.view = v;
        this.view.addMenutItemLoadEventHandler(e -> {
            try {
                this.model.load("/Volumes/ESD-USB/Music/youtube/trueshot/");
            }
            catch(InvalidFileException ex){
                System.out.println(ex.getMessage());
            }
        });
        this.view.addMenutItemCloseEventHandler(e -> exit());
        this.view.addmenuItemAboutEventHandler(e->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Musicplayer");
            alert.setContentText("Version Beta 0.1\n" +
                    "Keybindings \n" +
                    "alt + F1 : Help menu\n" +
                    "alt + F4 : Close Application\n" +
                    "alt + L  : Load Directory\n" +
                    "alt + D  : Open Song Details\n" +
                    "Space    : Toggle Play / Pause");

            alert.showAndWait();});

        this.view.addmenuItemPlayPauseEventHandler(e->playPause());
        this.view.addButtonPlayPauseEventHandler(e->playPause());

    }

    void playPause(){
        // Toggle play pause
        view.togglePlayPause();
    }
}
