package controller;


import view.*;
import core.*;
import interfaces.*;
import javafx.scene.control.*;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.FileChooser;
import ApplicationException.DatabaseException;

import static core.SelectedSongList.Library;
import static core.SelectedSongList.PlayList;

/**
 * This class provides loading functionality
 *
 * Written by Nils Milewski (nimile)
 */
public class LoadController implements interfaces.IController {

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

		tableName = SelectedSongList.PlayList;
	}

	/**
	 * Handles button "local" click event
	 */
	private void btLocalClicked() {

		List<File> files = EmptyView.getFiles();

		// check if any file are selected
		if(files != null) model.loadAllSongsFromFile(files);
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
	 * Handle button XML click event
	 */
	private void btXmlClicked() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*(*.xml)","*.xml"));
		chooser.setTitle("Load file...");
		File file = chooser.showOpenDialog(null);
		if(file == null) return;
		try {
			if(PlayList == tableName){
				XMLStrategy xmlStrategy = new XMLStrategy(file.getPath(), "");
				xmlStrategy.openReadablePlaylist();
				for(ISong s : xmlStrategy.getPlayList())
					model.getQueue().add(s);
			}
			else {
				XMLStrategy xmlStrategy = new XMLStrategy("", file.getPath());
				xmlStrategy.openReadableSongs();
				for (ISong s : xmlStrategy.getLibrary())
					model.getAllSongs().add(s);
			}
		} catch (IOException |  ArrayIndexOutOfBoundsException ex) {
			System.out.println("IOException or ArrayIndexOutOfBounds occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button binary click event
	 */
	private void btBinClicked() {FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*(*.bin)","*.bin"));
		chooser.setTitle("Load file...");
		File file = chooser.showOpenDialog(null);
		if(file == null) return;

		try {
			if(PlayList == tableName) {
				strategy = new Binary("", file.getAbsolutePath(), model.getAllSongs(), model.getQueue());
				ISongList tmp = ((Binary) strategy).readPl();
				for (ISong s : tmp) {
					System.out.println("ADDED " + s.toString() + "TO PL");
					model.getQueue().add(s);
				}
			}
			else{
			strategy = new Binary( file.getPath(), "", model.getAllSongs(), model.getQueue());
			ISongList tmp = ((Binary) strategy).readSongs();
			for (ISong s : tmp) {
				System.out.println("ADDED " + s.toString() + "TO LIB");
				model.getAllSongs().add(s);
			}
			}
		}catch (IOException |  ArrayIndexOutOfBoundsException ex) {
			System.out.println("IOException or ArrayIndexOutOfBounds occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle Button DB click event
	 */
	private void btDbClicked() {
		try {
			File dbFile = EmptyView.getFile("SQL Database", "*.db");
			if(dbFile == null) return;
			if(dbFile.getPath().contains(";"))throw new DatabaseException("SQL INJECTION DETECTED");

			 strategy = JDBCStrategy.getInstance(view.getLogin(), tableName, dbFile.getPath());
			// NOTE i mus cast the strategy to the specific class to use every method from the class
			// Im using try-resource for DB access
			// => nasty cleanup is not required with this method
			// => less potential exception
			// Adding songs
			if(PlayList.equals(tableName))
				for(ISong s : ((JDBCStrategy)strategy).readTable())
					model.getQueue().add(s);
			else
				for(ISong s : ((JDBCStrategy)strategy).readTable())
					model.getAllSongs().add(s);
			view.close();
		} catch (DatabaseException ex) {
			System.err.println("[SYS][CRIT] SQL INJECTION DETECTED! at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}catch (SQLException ex) {
			System.err.println("[CRIT] SQLException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button JPA click event
	 */
	private void btJPAClicked() {
		try{
			strategy = OpenJPAStrategy.getInstance(tableName);

			if(PlayList.equals(tableName))
				for(ISong s : ((OpenJPAStrategy)strategy).readSongs())
					model.getQueue().add(s);
			else
				for(ISong s : ((OpenJPAStrategy)strategy).readSongs())
					model.getAllSongs().add(s);

			view.close();

		} catch (Exception ex){
			System.err.println("[CRIT] Exception occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Toggles DB access
	 */
	private void cbEnabledEvent() {
		view.toggleDbView();
	}

}
