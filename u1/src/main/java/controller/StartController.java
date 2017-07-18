package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

/**
 * Created by thahnen on 12.07.17.
 */
public class StartController {

    @FXML private CheckBox ChkServer;
    @FXML private CheckBox ChkClient;
    @FXML private CheckBox ChkNormal;

    @FXML private Button BtnStart;

    @FXML private TextField TxtServerIP;
    @FXML private TextField TxtUDPPort;
    @FXML private TextField TxtTCPPort;

    @FXML private TextField TxtUser;
    @FXML private PasswordField TxtPass;

    public StartController() {

    }

    @FXML protected void onBtnStart(ActionEvent event)  throws RemoteException{
        if (ChkServer.isSelected()) {
            // in die Server GUI wechseln
            // diese View schliessen (oder zurückgeben an Main, die die dann schliesst)
        } else if (ChkNormal.isSelected()) {
            // in die normale GUI wechseln
            // diese View schliessen (oder zurückgeben an Main, die die dann schliesst) 
        } else if (ChkClient.isSelected()) {
            // in die Client GUI wechseln,
            // diese View schliessen (oder zurückgeben an Main, die die dann schliesst)
            // TODO: 18.07.17 Inhalte der TextBoxen für den TCP-/UDP-Client verarbeiten
        }
    }

    @FXML protected void onChkServer(ActionEvent event)  throws RemoteException{
        if (ChkClient.isSelected() || ChkNormal.isSelected()) {
            ChkClient.setSelected(false);
            ChkNormal.setSelected(false);
        }

        BtnStart.setDisable(false);

        TxtServerIP.setDisable(true);
        TxtUDPPort.setDisable(true);
        TxtTCPPort.setDisable(true);
        TxtUser.setDisable(true);
        TxtPass.setDisable(true);
    }

    @FXML protected void onChkClient(ActionEvent event)  throws RemoteException{
        if (ChkServer.isSelected() || ChkNormal.isSelected()) {
            ChkServer.setSelected(false);
            ChkNormal.setSelected(false);
        }

        // TODO: Noch fancy machen, damit man mindestens User und Password eingeben hat!
        BtnStart.setDisable(false);

        TxtServerIP.setDisable(false);
        TxtUDPPort.setDisable(false);
        TxtTCPPort.setDisable(false);
        TxtUser.setDisable(false);
        TxtPass.setDisable(false);
    }

    @FXML protected void onChkNormal(ActionEvent event)  throws RemoteException{
        if (ChkClient.isSelected() || ChkServer.isSelected()) {
            ChkClient.setSelected(false);
            ChkServer.setSelected(false);
        }

        BtnStart.setDisable(false);

        TxtServerIP.setDisable(true);
        TxtUDPPort.setDisable(true);
        TxtTCPPort.setDisable(true);
        TxtUser.setDisable(true);
        TxtPass.setDisable(true);
    }
}
