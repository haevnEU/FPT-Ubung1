package core;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by nilsmilewski on 08.05.17.
 */
public class util {
    // http://code.makery.ch/blog/javafx-dialogs-official/
     public static void showExceptionMessage(String title, String header, String stackTrace) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.setContentText("Fatal InvalidFileException occurred\nPlease submit the following StackTrace at GitHub");

        // Create expandable Exception.

        Label label = new Label("Exception stacktrace:");



       TextArea textArea = new TextArea("Time: " +  getDateWithTime() + " \n\nStackTrace\n" +stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static String getDateWithTime(){
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
        GregorianCalendar now = new GregorianCalendar();

        return df.format(now.getTime());
    }
}
