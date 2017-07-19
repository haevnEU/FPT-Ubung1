package controller;


import applicationException.UnknownApplicationException;
import view.*;
import core.*;
import java.io.*;
import interfaces.*;
import javafx.scene.control.*;

import java.util.List;
import java.sql.SQLException;

import applicationException.DatabaseException;

import static core.SelectedSongList.*;

/**
 * This class provides loading functionality
 *
 * Written by Nils Milewski (nimile)
 */
public class LoadController implements IController {

	private LoadView view;
	private Model model;
	private ISerializableStrategy strategy;

	// Used to differ between different tables
	private SelectedSongList tableName;

	/**
	 * link model with view
	 *
	 * @param m Application model
	 * @param v MainView.MainView to link
	 */
	@Override
	public void link(IModel m, IView v) {
		model = (Model)m;
		view = (LoadView)v;

		view.addBtLocalClickEventHandler(e -> btLocalClicked());
		view.addBtXmlClickEventHandler(e -> btXmlClicked());
		view.addBtBinClickEventHandler(e -> btBinClicked());
		view.addBtDbClickEventHandler(e -> btDbClicked());
		view.addBtOpenJPAClickEventHandler(e -> btJPAClicked());
		view.addCheckBoxDbEnableEventHandler(e -> cbEnabledEvent());
		view.addToggleSongList((observable, oldValue, newValue) -> onToggle(newValue));

		tableName = SelectedSongList.Library;
	}
    /**
     * Toggles between SongList selection
     * @param newValue New value which represent a SongList selected by a RadioButton
     */
    private void onToggle(Toggle newValue) {
        // Creates a local copy of the selected RadioButton
        RadioButton rb = (RadioButton) newValue;
        if(rb.getId().equals(Library.toString())) tableName = Library;
        else tableName = SelectedSongList.PlayList;
    }

    /**
	 * Handles button "local" click event
	 */
	private void btLocalClicked() {
		List<File> files = FileViewer.getFiles("MP3 Files", "*.mp3");
		// check if any file are selected
		if(files != null) model.loadAllSongsFromFile(files);
	}

	/**
	 * Handle button XML click event
	 */
	private void btXmlClicked() {

		File file =FileViewer.getFile("XML", "*.xml");
		if(file == null) return;
		try {
			if(PlayList == tableName){
				XMLStrategy xmlStrategy = new XMLStrategy(file.getPath(), "");
				xmlStrategy.openReadablePlaylist();
				for(ISong s : xmlStrategy.getPlayList()) addSong(s, PlayList);
			}
			else {
				XMLStrategy xmlStrategy = new XMLStrategy("", file.getPath());
				xmlStrategy.openReadableSongs();
				for (ISong s : xmlStrategy.getLibrary()) addSong(s, Library);
			}
		} catch (IOException |  ArrayIndexOutOfBoundsException ex) {
			System.out.println("IOException or ArrayIndexOutOfBounds occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button binary click event
	 */
	private void btBinClicked() {
        File file = FileViewer.getFile("Binary", "*.bin");
        if (file == null) return;

        try {
            if (PlayList == tableName) {
                strategy = new Binary("", file.getAbsolutePath(), model.getLibrary(), model.getQueue());
                ISongList tmp = ((Binary) strategy).readPl();
                for (ISong s : tmp) addSong(s, PlayList);
            } else {
                strategy = new Binary(file.getPath(), "", model.getLibrary(), model.getQueue());
                ISongList tmp = ((Binary) strategy).readSongs();
                for (ISong s : tmp) addSong(s, Library);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("IOException or ArrayIndexOutOfBounds occurred at " + Util.getUnixTimeStamp());
            ex.printStackTrace(System.err);
        }
    }

	/**
	 * Handle Button DB click event
	 */
	private void btDbClicked() {
		try {
			// TODO well it could be better implemented... suggestion use resource loader
			File dbFile = new File("src/main/resources/music.db");
			if (Model.isCustomDBFeaturesEnabled()) {
				dbFile = FileViewer.getFile("SQL Database", "*.db");
				if (dbFile == null) return;
			}
			if (dbFile.getPath().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");

			strategy = JDBCStrategy.getInstance(view.getLogin(), tableName, dbFile.getPath());
			// NOTE i mus cast the strategy to the specific class to use every method from the class
			// Im using try-resource for DB access
			// => nasty cleanup is not required with this method
			// => less potential exception
			// Adding songs
			if (PlayList.equals(tableName))
				for (ISong s : ((JDBCStrategy) strategy).readTable()) addSong(s, PlayList);
			else
				for (ISong s : ((JDBCStrategy) strategy).readTable()) addSong(s, Library);

			view.close();
		} catch (DatabaseException ex) {
			System.err.println("[SYS][CRIT] SQL INJECTION DETECTED! at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		} catch (SQLException ex) {
			System.err.println("[CRIT] SQLException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button JPA click event
	 */
	private void btJPAClicked() {
		try {
			if (Model.isCustomDBFeaturesEnabled()) {
				File dbFile = FileViewer.getFile("SQL Database", "*.db");
				if (dbFile == null) return;
				if (dbFile.getPath().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");
				strategy = OpenJPAStrategy.getInstance(view.getLogin(), dbFile.getPath(), tableName);
			} else strategy = OpenJPAStrategy.getInstance(tableName);

			if (PlayList.equals(tableName))
				for (ISong s : ((OpenJPAStrategy) strategy).readSongs()) addSong(s, PlayList);
			else
				for (ISong s : ((OpenJPAStrategy) strategy).readSongs()) addSong(s, Library);

			view.close();

		} catch (Exception ex) {
			System.err.println("[CRIT] Exception occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	private void addSong(ISong song, SelectedSongList selectedSongList){
		switch (selectedSongList){
			case Library:
				try {
					// Check if ID did not exists
					if (!IDGenerator.existsID(song.getId())) {
						IDGenerator.addId(song.getId());
						model.getLibrary().add(song);
					}
				} catch (UnknownApplicationException ex) {
					System.err.println("[CRIT][JDBCStrategy] Exception occurred: " + ex.getMessage());
					ex.printStackTrace(System.err);
				}
				break;

			case PlayList:
				model.getQueue().add(song);
				break;
		}
	}
	/**
	 * Toggles DB access
	 */
	private void cbEnabledEvent() {
		view.toggleDbView();
	}
}