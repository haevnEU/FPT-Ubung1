package controller;

import core.*;
import interfaces.*;
import javafx.scene.control.*;

import javafx.stage.FileChooser;
import view.EmptyView;
import view.SaveView;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import ApplicationException.DatabaseException;

import static core.SelectedSongList.Library;
import static core.SelectedSongList.PlayList;


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
		tableName = SelectedSongList.PlayList;
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
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*(*.xml)","*.xml"));
		chooser.setTitle("Load file...");
		File file = chooser.showSaveDialog(null);

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
			if(PlayList == tableName){
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
			File dbFile = new File("src/main/resources/music.db");
			if(Model.isCustomDBFeaturesEnabled()){
				dbFile = EmptyView.getFile("SQL Database", "*.db");
				if(dbFile == null) return;
			}
			if(dbFile.getPath().contains(";"))throw new DatabaseException("SQL INJECTION DETECTED");

			strategy = JDBCStrategy.getInstance(view.getLogin(), tableName, dbFile.getPath());

			// NOTE i mus cast the strategy to the specific class to use every method from the class
			// Im using try-resource for DB access
			// => nasty cleanup is not required with this method
			// => less potential exception
			if(PlayList == tableName) ((JDBCStrategy)strategy).writeSongList(model.getQueue());
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
		try {
			strategy = OpenJPAStrategy.getInstance(tableName);
			if(PlayList == tableName) {
				strategy.openWriteablePlaylist();
				for(ISong s : model.getQueue())
				{
					PlayList s2 = new PlayList(s, false);
					strategy.writeSong(s2);
				}
				strategy.closeWriteable();
			}
			else  {
				strategy.openWriteableSongs();
				for(ISong s : model.getAllSongs())
				{
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
