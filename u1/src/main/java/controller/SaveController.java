package controller;

import core.*;
import interfaces.*;
import javafx.scene.control.*;

import javafx.stage.FileChooser;
import view.SaveView;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javafx.stage.DirectoryChooser;
import ApplicationException.DatabaseException;

import javax.sql.rowset.serial.SerialRef;

import static core.SelectedSongList.Library;
import static core.SelectedSongList.Playlist;


/**
 * This class provides functionality for saving
 *
 * Written by Nils Milewski (nimile)
 */
public class SaveController implements interfaces.IController {

	private SaveView view;
	private Model model;

	private SongList list;
	private SelectedSongList tableName;

	private ISerializableStrategy strategy;
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
		tableName = SelectedSongList.Playlist;
	}

	private void onToggle(Toggle newValue) {
		RadioButton rb = (RadioButton) newValue;
		if(rb.getId().equals(Library.toString()))tableName = Library;
		else tableName = SelectedSongList.Playlist;
	}

	/**
	 * Handle button XML click event
	 */
	private void btXmlClicked() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*(*.xml)","*.xml"));
		chooser.setTitle("Load file...");
		File file = chooser.showSaveDialog(null);

		if(file == null) return;
		try {

			if(Playlist == tableName){
				strategy = new XMLStrategy(file.getPath(),"");

				list = model.getQueue();

				strategy.openWriteablePlaylist();
				for(ISong s : list) strategy.writeSong(s);
				strategy.closeWriteable();
			}
			else {
				strategy = new XMLStrategy("", file.getPath());

				list = model.getAllSongs();
				strategy.openWriteableSongs();
				for(ISong s : list) strategy.writeSong(s);
				strategy.closeWriteable();
			}
		} catch (IOException ex) {
			System.err.println("[CRIT] IOException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
	}

	/**
	 * Handle button binary click event
	 */
	private void btBinClicked() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*(*.bin)","*.bin"));
		chooser.setTitle("Save...");
		File file = chooser.showSaveDialog(null);

		if(file == null) return;
		try {
			if(Playlist == tableName){
				strategy = new Binary("", file.getPath(),model.getAllSongs(), model.getQueue());
				((Binary)strategy).writePl();
			}
			else {
				strategy = new Binary(file.getAbsolutePath(), "", model.getAllSongs(), model.getQueue());
				((Binary) strategy).writeSongs();
			}
		} catch (RemoteException ex) {
			System.err.println("[CRIT] RemoteException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}
		catch (IOException ex) {
			System.err.println("[CRIT] IOException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace(System.err);
		}

	}

	/**
	 * Handle Button DB click event
	 */
	private void btDbClicked() {
		try {
			strategy = JDBCStrategy.getInstance(view.getLogin(), tableName);
			// NOTE i mus cast the strategy to the specific class to use every method from the class
			// Im using try-resource for DB access
			// => nasty cleanup is not required with this method
			// => less potential exception
			if(Playlist == tableName) ((JDBCStrategy)strategy).writeSongList(model.getQueue());
			else ((JDBCStrategy)strategy).writeSongList(model.getAllSongs());
			view.close();
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
