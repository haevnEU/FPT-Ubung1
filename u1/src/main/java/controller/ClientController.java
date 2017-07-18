package controller;

import core.Song;
import core.SongList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Created by thahnen on 12.07.17.
 */
public class ClientController {

    @FXML private ListView LstMp3;
    @FXML private ListView LstPl;

    @FXML private Button BtnShin;

    @FXML private Button BtnSletzt;
    @FXML private Button BtnSstart;
    @FXML private Button BtnSnaechs;

    @FXML private Label LblSTitel;
    @FXML private Label LblSZeit;
    @FXML private Label LblIPAdress;

    private Song selectedMp3;

    // TODO: 18.07.17 Am besten Referenz auf TCP-/UDP-Client mitgeben
    // TODO: => dann werden die Controls eingegraut (bei erfolgreicher Anmeldung)
    // TODO: => das Label Lbl LblIPAdress wird angepasst (bei erfolgreicher Anmeldung)
    // TODO: => die anderen Elemente werden angepasst/ gefüllt
    public ClientController() {
        this.selectedMp3 = null;

        // Hier die IP eingeben
        this.LblIPAdress.setText("<IP>");

        // Hier die Werte vom Server für die SongList(s) eingeben
        this.LstMp3.setItems(new SongList());
        this.LstPl.setItems(new SongList());

        // Hier die Werte vom Server für den momentanten Song eingeben
        this.LblSTitel.setText("<SongTitel>");
        this.LblSZeit.setText("<SongZeit>");

        this.BtnShin.setDisable(false);
        this.BtnSletzt.setDisable(false);
        this.BtnSstart.setDisable(false);
        this.BtnSnaechs.setDisable(false);
    }

    @FXML protected void onBtnShin(ActionEvent event) {
        if (this.selectedMp3 != null) {
            // TODO: 18.07.17 Action an Server senden, auf Antwort warten, dann View ändern
            // TODO: Es muss nur die ListView LstPl & LstMp3

            this.selectedMp3 = null;
            this.LstMp3.getSelectionModel().clearSelection();
        }
    }

    @FXML protected void onBtnSletzt(ActionEvent event) {
        // TODO: 18.07.17 Action an Server senden, auf Antwort warten, dann View ändern
        // TODO: Es müssen die Label LblSTitel und LblSZeit angepasst werden

        this.LblSTitel.setText("<letzter SongTitel>");
        this.LblSZeit.setText("<Songzeit vom letzten Song>");
    }

    @FXML protected void onBtnSstart(ActionEvent event) {
        // TODO: 18.07.17 Action an Server senden, auf Antwort warten, dann View ändern
        // TODO: Es müssen das Label LblSZeit angepasst werden
        // TODO: => vlt muss auch gar nichts gemacht werden

        this.LblSZeit.setText("<pausiert oder so>");

    }

    @FXML protected void onBtnSnaechs(ActionEvent event) {
        // TODO: 18.07.17 Action an Server senden, auf Antwort warten, dann View ändern
        // TODO: Es müssen die Label LblSTitel und LblSZeit angepasst werden

        this.LblSTitel.setText("<nächster SongTitel>");
        this.LblSZeit.setText("<Songzeit vom nächsten Song>");
    }

    @FXML protected void onMp3MsPressed(MouseEvent event) {
        // Es muss nur abgefangen werden, ob die ListView angeklickt wurde, bevor es eingegraut wurde
        if (!this.LstMp3.isDisabled()) {
            this.selectedMp3 = (Song)LstMp3.getFocusModel().getFocusedItem();
        }
    }
}
