package controller;

import core.*;
import interfaces.*;
import javafx.scene.control.*;

import view.SaveView;
import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.DirectoryChooser;
import javafx.beans.value.ObservableValue;
import ApplicationException.DatabaseException;

import static core.SelectedSongList.Library;
import static core.SelectedSongList.Playlist;


/**
 * This class provides functionality for saving
 *
 * Written by Nils Milewski (nimile)
 */
public class SaveController implements interfaces.IController {

	SaveView view;
	Model model;

	SongList list;
	SelectedSongList tableName;
	/**
	 * link model with view
	 *
	 * @param m Application model
	 * @param v MainView.MainView to link
	 */
	@Override
	public void link(IModel m, IView v) {
		model = (Model)m;
		view = (SaveView)v;

		view.addBtXmlClickEventHandler(e -> btXmlClicked());
		view.addBtBinClickEventHandler(e -> btBinClicked());
		view.addBtDbClickEventHandler(e -> btDbClicked());
		view.addBtOpenJPAClickEventHandler(e -> btJPAClicked());
		view.addCheckBoxDbEnableEventHandler(e -> cbEnabledEvent());
		view.addToggleSongList((observable, oldValue, newValue) -> onToggle(newValue));

		list = model.getQueue();
		tableName = SelectedSongList.Library;
	}

	private void onToggle(Toggle newValue) {
		RadioButton rb = (RadioButton) newValue;
		if(rb.getId() == Library.toString())tableName = Library;
		else tableName = SelectedSongList.Playlist;
	}

	/**
	 * Handle button XML click event
	 */
	private void btXmlClicked() {
		String path = (new DirectoryChooser()).showDialog(null).getAbsolutePath();
		System.out.println(path);
	}

	/**
	 * Handle button binary click event
	 */
	private void btBinClicked() {
		String path = (new DirectoryChooser()).showDialog(null).getAbsolutePath();
		System.out.println(path);
	}

	/**
	 * Handle Button DB click event
	 */
	private void btDbClicked() {
		try {
			JDBCStrategy jdbcStrategy = JDBCStrategy.getInstance(view.getLogin(), tableName);
			if(Playlist == tableName) jdbcStrategy.writeSongList(model.getQueue());
			else jdbcStrategy.writeSongList(model.getAllSongs());
		} catch (DatabaseException e) {
			System.err.println("[SYS][CRIT] SQL INJECTION DETECTED! at " + Util.getUnixTimeStamp());
			e.printStackTrace(System.err);
		}catch(SQLException | IOException ex) {
			System.err.println("[CRIT] Exception thrown at " + Util.getUnixTimeStamp());
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
