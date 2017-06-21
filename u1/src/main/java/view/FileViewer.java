package view;

import java.util.*;

import java.io.File;
import javafx.stage.FileChooser;
import ApplicationException.RichException;

/**
 * THis class is used to open a single instance of loading file window
 */
public class FileViewer implements interfaces.IView {

    @Override
    public void destroy() {
    }

    /**
     * Invokes a new multiple file selection window
     *
     * @param description Describes the File format (MP3 File, Text file, etc)
     * @param extension   Describes the extension (*.mp3, *.txt, etc)
     * @return selected files as a List
     */
    public static List<File> getFiles(String description, String extension) {
        List<File> selectedFiles = null;
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description, extension));
            selectedFiles = fc.showOpenMultipleDialog(null);

        } catch (Exception ex) {
            new RichException(ex);
        }
        return selectedFiles;
    }

    /**
     * Invokes a new single file selection window
     *
     * @param description Describes the File format (MP3 File, Text file, etc)
     * @param extension   Describes the extension (*.mp3, *.txt, etc)
     * @return Selected file
     */
    public static File getFile(String description, String extension) {
        File file = null;
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
            file = fc.showOpenDialog(null);
        } catch (Exception ex) {
            new RichException(ex);
        }
        return file;
    }

    /**
     * Invokes a new single file saving window
     *
     * @param description Describes the File format (MP3 File, Text file, etc)
     * @param extension   Describes the extension (*.mp3, *.txt, etc)
     * @return Selected file
     */
    public static File saveFile(String description, String extension) {
        File file = null;
        try {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
            file = fc.showSaveDialog(null);
        } catch (Exception ex) {
            new RichException(ex);
        }
        return file;
    }


}
