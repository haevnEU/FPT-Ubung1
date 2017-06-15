package controller;


import ApplicationException.DatabaseException;
import core.JDBCStrategy;
import core.Model;
import core.SelectedSongList;
import core.Util;
import interfaces.IModel;
import interfaces.ISong;
import interfaces.IView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import view.EmptyView;
import view.LoadView;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static core.SelectedSongList.Library;
import static core.SelectedSongList.Playlist;

/**
 * This class provides loading functionality
 *
 * Written by Nils Milewski (nimile)
 */
public class LoadController implements interfaces.IController {

	private LoadView view;
	private Model model;

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
		if(rb.getId() == Library.toString())tableName = Library;
		else tableName = SelectedSongList.Playlist;
	}

	/**
	 * Handle button XML click event
	 */
	private void btXmlClicked() {
	}

	/**
	 * Handle button binary click event
	 */
	private void btBinClicked() {	}

	/**
	 * Handle Button DB click event
	 */
	private void btDbClicked() {
		try {
			JDBCStrategy jdbcStrategy = JDBCStrategy.getInstance(view.getLogin(), tableName);

			// Adding songs
			if(Playlist == tableName)
				for(ISong s : jdbcStrategy.readTable())
					model.getQueue().add(s);
			else
				for(ISong s : jdbcStrategy.readTable())
					model.getAllSongs().add(s);
		} catch (DatabaseException e) {
			System.err.println("[SYS][CRIT] SQL INJECTION DETECTED! at " + Util.getUnixTimeStamp());
			e.printStackTrace(System.err);
		}catch (SQLException ex) {
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button JPA click event
	 */
	private void btJPAClicked() {
	}

	/**
	 * Toggles DB access
	 */
	private void cbEnabledEvent() {
		view.toggleDbView();
	}

}
