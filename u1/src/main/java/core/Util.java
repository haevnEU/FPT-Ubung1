package core;

import java.io.*;

import controller.DeleteController;
import controller.DetailController;
import controller.LoadController;
import controller.SaveController;
import interfaces.IController;
import interfaces.IModel;
import interfaces.ISong;
import interfaces.IView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.*;

public final class Util {

    /**
     * new instances are forbidden
     */
    private Util(){}

    /**
     * Returns timestamp based on unix time
     * @return current timestamp
     */
    public static Long getUnixTimeStamp(){
        return System.currentTimeMillis() / 1000L;
    }


    /**
     * Reads a file from disk
     * <i>could be used later</i>
     * @param path path to file
     * @return return file content
     * @throws IOException ...
     */
    public static String readFile(String path) throws IOException{
        StringBuilder line = new StringBuilder();
        if(!new File(path).exists()) throw new FileNotFoundException();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String inLine;
            while((inLine = br.readLine()) != null)
                line.append(inLine).append(" \n");
        }
        return line.toString();
    }


    /**
     * Convert any string into a equivalent hex string
     * @param text String which should be converted
     * @return converted hex string
     */
    public static String convertToHex(String text){
        System.out.println("[INFO] Encode to hexadecimal string \"" + text + "\"");
        StringBuilder out = new StringBuilder();
        for(char c : text.toCharArray()) {
            if(out.length() > 0)out.append(' ');
            out.append(String.format("%04x", (int)c));
        }
        return out.toString();
    }

    /**
     * Convert a Hex coded string into a JAVA string
     * @param text Hex string which should be decoded
     * @return decoded hex string
     */
    public static String convertToString(String text){
        System.out.println("[INFO] Decode to hexadecimal string \"" + text + "\"");
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
         if(text.charAt(i) != ' ')
            try {
                String tmp = text.substring(i, i+4);
                out.append((char)Integer.parseInt(tmp, 16));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {}
        }
        return out.toString();
    }

    /**
     * Shows an alert window
     * @param text text which should be shown
     * @param type alert type
     */
    public static void showAlert(String text, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Shows an alert window
     * @param text text which should be shown
     */
    public static void showAlert(String text){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(text);
        alert.showAndWait();
    }


    public static void invokeNewWindow(SceneType t, Model model){
        if(SceneType.DetailView != t) invokeNewWindow(t, model, null);
    }

    /**
     * Invokes any window
     * @param t which scene should be invoked, details could be seen inside view.SceneType
     * @param model
     * @param s
     */
    public static void invokeNewWindow(SceneType t, IModel model, ISong s){

        Scene tmpScene = new Scene(new BorderPane());
        Stage tmpStage = new Stage();
        IController tmpController;
        IView tmpView = new FileViewer();
        switch(t){
            case DeleteView:
                tmpController = new DeleteController();
                tmpView = DeleteView.getInstance(((Model)model).getQueue());
                if(tmpView == null) return;
                tmpScene = new Scene((DeleteView)tmpView);

                tmpController.link(model, tmpView);
                tmpStage.setTitle("Delete...");
                tmpStage.setWidth(250);
                tmpScene.setOnKeyPressed(e -> {
                    if(e.getCode() == KeyCode.ESCAPE) tmpStage.close();
                });
                break;

            case DetailView:
                tmpController = new DetailController();
                tmpView = new DetailView();
                tmpScene = new Scene((DetailView)tmpView);

                tmpController.link(model, tmpView);
                ((DetailController)tmpController).init((Song)s);

                tmpStage.setTitle(s.getTitle());
                tmpScene.setOnKeyPressed(e -> {
                    if(e.getCode() == KeyCode.ESCAPE) tmpStage.close();
                });
                break;

            case SaveView:
                tmpController = new SaveController();
                tmpView = SaveView.getInstance();
                if(tmpView == null) return;
                tmpScene = new Scene((SaveView)tmpView);
                tmpStage.setTitle("Save...");
                tmpController.link(model, tmpView);
                break;

            case LoadView:
                tmpController = new LoadController();
                tmpView = LoadView.getInstance();
                if(tmpView == null) return;
                tmpScene = new Scene((LoadView)tmpView);
                tmpStage.setTitle("Load...");
                tmpController.link(model, tmpView);
                break;
        }
        tmpStage.setScene(tmpScene);
        tmpStage.setResizable(false);
        tmpStage.showAndWait();
        tmpView.destroy();
    }

}














// QA
// Q: what is a stacktrace?
// A: a stacktrace is a stack which traces?
// ...
// ...
// Well a stacktrace shows the last executed method in a program
// it can help to locate possible errors, if you look at a stacktrace you now what i mean
// bellow is a stacktrace, but its modified
// we can see the first execution, the entry point and the last where the exception occurred
//java.lang.NullPointerException <-- Description of the exception
//        at MainController.MainController.menuItemLoadEventHandler(MainController.MainController.java:118) <-- occurrence of exception
//        at MainController.MainController.lambda$link$0(MainController.MainController.java:34)
//        at com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:218)
//        at ....
//        at java.lang.Thread.run(Thread.java:748) <-- entry point
