package controller;

import view.*;
import core.*;
import java.io.*;
import interfaces.*;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.rmi.RemoteException;
import applicationException.DatabaseException;

import static core.SelectedSongList.*;

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
		tableName = Library;
	}

	private void onToggle(Toggle newValue) {
		RadioButton rb = (RadioButton) newValue;
		if(rb.getId().equals(Library.toString()))tableName = Library;
		else tableName = SelectedSongList.PlayList;
	}

	/**
	 * Handle button XML click event
	 */
	private void btXmlClicked() {
	    File file = FileViewer.saveFile("XML", "*.xml");
		if(file == null) return;
		try {

			if(PlayList == tableName){
				strategy = new XMLStrategy(file.getPath(),"");

				list = model.getQueue();

				strategy.openWriteablePlaylist();
				for(ISong s : list) strategy.writeSong(s);
				strategy.closeWriteable();
			}
			else {
				strategy = new XMLStrategy("", file.getPath());

				list = model.getLibrary();
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
		File file = FileViewer.saveFile("Binary", "*.bin");

		if(file == null) return;
		try {
			if(PlayList == tableName){
				strategy = new Binary("", file.getPath(),model.getLibrary(), model.getQueue());
				((Binary)strategy).writePl();
			}
			else {
				strategy = new Binary(file.getAbsolutePath(), "", model.getLibrary(), model.getQueue());
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
			File dbFile = new File("src/main/resources/music.db");
			if(Model.isCustomDBFeaturesEnabled()){
				dbFile = FileViewer.getFile("SQL Database", "*.db");
				if(dbFile == null) return;
			}
			if(dbFile.getPath().contains(";"))throw new DatabaseException("SQL INJECTION DETECTED");

			strategy = JDBCStrategy.getInstance(view.getLogin(), tableName, dbFile.getPath());

			// NOTE i mus cast the strategy to the specific class to use every method from the class
			// Im using try-resource for DB access
			// => nasty cleanup is not required with this method
			// => less potential exception
			if(PlayList == tableName) ((JDBCStrategy)strategy).writeSongList(model.getQueue());
			else ((JDBCStrategy)strategy).writeSongList(model.getLibrary());
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
		try {
			if(Model.isCustomDBFeaturesEnabled()) {
				File dbFile = FileViewer.getFile("SQL Database", "*.db");
				if (dbFile == null) return;

				if (dbFile.getPath().contains(";")) throw new DatabaseException("SQL INJECTION DETECTED");
				strategy = OpenJPAStrategy.getInstance(view.getLogin(), dbFile.getPath(), tableName);
			}
			else strategy = OpenJPAStrategy.getInstance(tableName);
			if(PlayList == tableName) {
				strategy.openWriteablePlaylist();
				for(ISong s : model.getQueue()) {
					PlayList s2 = new PlayList(s, false);
					strategy.writeSong(s2);
				}
				strategy.closeWriteable();
			}
			else  {
				strategy.openWriteableSongs();
				for(ISong s : model.getLibrary()) {
					core.Library s2 = new Library(s, false);
					strategy.writeSong(s2);
				}
				strategy.closeWriteable();
			}
		} catch (DatabaseException e) {
			System.err.println("[SYS][CRIT] SQL INJECTION DETECTED! at " + Util.getUnixTimeStamp());
			e.printStackTrace(System.err);
		}catch(IOException ex) {
			System.err.println("[CRIT] Exception thrown at " + Util.getUnixTimeStamp());
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
