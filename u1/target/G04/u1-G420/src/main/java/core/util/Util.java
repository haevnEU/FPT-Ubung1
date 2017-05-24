package core.util;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;

/**
 * This class provides utility functions
 */
public final class Util {


    /**
     * Shows an exception message
      * @param e exception which should displayed
     */
    public static void showExceptionMessage(Exception e) {

        String title, header, stackTrace;

//        used to format the exception stacktrace
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        title = "Music player v.01";
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

//        The grid should grow in both directions
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        Label bottomLabel = new Label("......!");

        GridPane gridContent = new GridPane();
        gridContent.setMaxWidth(Double.MAX_VALUE);
        gridContent.add(label, 0, 0);
        gridContent.add(textArea, 0, 1);
        gridContent.add(bottomLabel,0,2);

//         Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(gridContent);

        alert.showAndWait();
    }

	/**
	 * Shows a warning message window
	 * @param s string which should shown to the user
	 */
	public static void showWarningMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Warning occurred");
        alert.setContentText(s);

        alert.show();

    }

    /**
     * Returns timestamp based on unix time
     * @return current timestamp
     */
    private static Long getUnixTimeStamp(){
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
        String line = "";
        if(!new File(path).exists()) throw new FileNotFoundException();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String inLine;
            while((inLine = br.readLine()) != null){
                line += inLine +" \n";
            }
        }
        return line;
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
//        at Controller.Controller.menuItemLoadEventHandler(Controller.Controller.java:118) <-- occurrence of exception
//        at Controller.Controller.lambda$link$0(Controller.Controller.java:34)
//        at com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:218)
//        at ....
//        at java.lang.Thread.run(Thread.java:748) <-- entry point
