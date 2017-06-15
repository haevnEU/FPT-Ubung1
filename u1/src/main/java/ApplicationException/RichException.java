package ApplicationException;

import java.io.*;

import core.Util;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


/**
 * This class provides a GUI for exception
 *
 * Written by Nils Milewski (nimile)
 */
public final class RichException extends Exception implements interfaces.IApplicationException {

	/**
	 * Creates a new RichException window using ApplicationException details
	 *
	 * @param ex ApplicationException details
	 */
	public RichException(Exception ex) {
		super(ex);
		String title, header, stackTrace;
		System.err.println("[NOTE] Richexception thrown at " + Util.getUnixTimeStamp());
//        used to format the exception stacktrace
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);

		title = "Music player v.01";
		header = ex.getMessage();
		stackTrace = sw.toString();


//        Create a simple alert window
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText("Fatal exception occurred\nPlease submit the following StackTrace at GitHub");

//        The following lines are modify from the default alert
		Label label = new Label("ApplicationException stacktrace:");

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

//         Set expandable ApplicationException into the dialog pane.
		alert.getDialogPane().setExpandableContent(gridContent);

		alert.showAndWait();
	}
}
