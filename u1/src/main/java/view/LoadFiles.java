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
public class LoadFiles implements interfaces.IView {

	// used singleton pattern
	private LoadFiles(){};
	private static boolean instanced = false;
	private static LoadFiles instance;
	public static LoadFiles getInstance() {
		if(instanced && instance != null) return null;
		instanced = true;
		instance = new LoadFiles();
		return instance;
	}

	@Override
	public void destroy() {
		instanced = false;
		instance = null;
	}

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
