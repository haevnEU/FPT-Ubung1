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

public class InvalidFileException extends Exception {
    public InvalidFileException(){

        super("InvalidFileOperation occured");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        super.printStackTrace(pw);
        util.showExceptionMessage("Fatal Error occurred", super.getMessage(), sw.toString());
    }





}
