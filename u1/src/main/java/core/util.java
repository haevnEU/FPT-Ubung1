package core;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.*;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility functions
 */
public class util {


    /**
     * Shows an exception message
      * @param e exception whicht should displayed
     */
    public static void showExceptionMessage(Exception e) {

//         well if there no exception what should we display?
        if(e == null) return;

        String title, header, stackTrace;

//        used to format the exception stacktrace
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        title = "Musikplayer v.01";
        header = e.getMessage();
        stackTrace = sw.toString();


//        Create a simple alert window
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText("Fatal exception occurred\nPlease submit the following StackTrace at GitHub");

//        The following lines are modify from the default alert
        Label label = new Label("Exception stacktrace:");

//        A non editable TextArea is used do display the stacktrace
        TextArea textArea = new TextArea("TimeStamp: " +  getUnixTimeStamp() + " \n\nStackTrace\n" +stackTrace);
        textArea.setEditable(false);

//        The grid should grow in both directorys
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane gridCntent = new GridPane();
        gridCntent.setMaxWidth(Double.MAX_VALUE);
        gridCntent.add(label, 0, 0);
        gridCntent.add(textArea, 0, 1);

//         Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(gridCntent);

        alert.showAndWait();
    }

    /**
     * Returns current timestamp based on unix time
     * @return
     */
    public static Long getUnixTimeStamp(){
        return System.currentTimeMillis() / 1000L;
    }


    /**
     * Loads localization className for specific class
     * <p>File structure: objectname:text where objectname should be a UI element and text the string</p>
     * @param className name of the class which should be loaded
     * @return
     */
    public static HashMap<String, String>load(String className){

        // Check if file exists
        if(!(new File(className).exists())) return null;

        HashMap<String, String> map = new HashMap<>();

        // Read file and add qualified lines to our map
        try(BufferedReader br = new BufferedReader(new FileReader(className))){
            String line;
            while((line = br.readLine()) != null)
                if(line.contains(":"))
                    map.put(line.split(":")[0],line.split(":")[1]);

        } catch(FileNotFoundException e){
            showExceptionMessage(e);
        } catch(IOException e){
            showExceptionMessage(e);
        } catch (Exception e){
            showExceptionMessage(e);
        }
        finally {
            return map;
        }
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
// we can see the first execution, the entrypoint and the last where the exception occurred
//java.lang.NullPointerException <-- Description of the exception
//        at Controller.menuItemLoadEventHandler(Controller.java:118) <-- occurrence of exception
//        at Controller.lambda$link$0(Controller.java:34)
//        at com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:218)
//        at ....
//        at java.lang.Thread.run(Thread.java:748) <-- entrypoint
