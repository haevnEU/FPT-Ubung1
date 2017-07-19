package applicationException;

import java.io.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import core.Util;
import interfaces.IApplicationException;


/**
 * This class provides a GUI for exception
 *
 * Written by Nils Milewski (nimile)
 */
public final class RichException extends Exception implements IApplicationException {

	/**
	 * Creates a new RichException window using applicationException details
	 *
	 * @param ex applicationException details
	 */
	public RichException(Exception ex) {
		super(ex);
		String title, header, stackTrace;
		System.err.println("[NOTE] Richexception thrown at " + Util.getUnixTimeStamp());
//        used to format the exception stacktrace
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		System.err.println("[CRIT] Exception occurred at " + Util.getUnixTimeStamp());
		ex.printStackTrace(System.err);

		title = "Music player v.01";
		header = ex.getMessage();
		stackTrace = sw.toString();


//        Create a simple alert window
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText("Fatal exception occurred\nPlease submit the following StackTrace at GitHub");

//        The following lines are modify from the default alert
		Label label = new Label("applicationException stacktrace:");

//        A non editable TextArea is used do display the stacktrace
		TextArea textArea = new TextArea("TimeStamp: " + core.Util.getUnixTimeStamp() + " \n\nStackTrace\n" + stackTrace);
		textArea.setEditable(false);

//        The grid should grow in both directions
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		Label bottomLabel = new Label("......!");

		GridPane gridContent = new GridPane();
		gridContent.setMaxWidth(Double.MAX_VALUE);
		gridContent.add(label, 0, 0);
		gridContent.add(textArea, 0, 1);
		gridContent.add(bottomLabel, 0, 2);

//         Set expandable applicationException into the dialog pane.
		alert.getDialogPane().setExpandableContent(gridContent);

		alert.showAndWait();
	}
}
