package view;

import java.util.*;

import ApplicationException.RichException;
import core.Util;
import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


/**
 * THis class is used to open a single instance of loading file window
 */
public class EmptyView implements interfaces.IView {

	@Override
	public void destroy() {}

	/**
	 * Returns selected file
	 * @return selected files as a List
	 */
	public static List<File> getFiles(){
		List<File> selectedFiles = new ArrayList<>();
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
			selectedFiles = fc.showOpenMultipleDialog(null);
			if (selectedFiles == null) return null;

		} catch (Exception ex) {
			Util.showExceptionMessage(ex);
		}

		return selectedFiles;
	}

	public static File getFile(String description, String extension){
		File file = null;
		try{
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
			file = fc.showOpenDialog(null);
		}catch (Exception ex){
			new RichException(ex);
		}
		return file;
	}
}
