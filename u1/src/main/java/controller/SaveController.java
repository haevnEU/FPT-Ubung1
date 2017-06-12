package controller;


import core.JDBCStrategy;
import core.LoginCredentials;
import core.Model;
import core.SongList;
import interfaces.IDatabaseUtils;
import interfaces.IModel;
import interfaces.IView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.DirectoryChooser;
import view.LoadFiles;
import view.SaveView;

import java.io.IOException;

public class SaveController implements interfaces.IController {

	SaveView view;
	Model model;

	SongList list;
	String tableName;
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
		view.addToggleSongList((observable, oldValue, newValue) -> onToggle(observable, oldValue, newValue));

		list = model.getQueue();
		tableName = "Library";
	}

	private void onToggle(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
		RadioButton rb = (RadioButton) newValue;
		if(rb.getId().toUpperCase().startsWith("Q")){
			list = model.getQueue();
			tableName = "Library";
		}
		else{
			list = model.getAllSongs();
			tableName = "Playlist";
		}
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
		JDBCStrategy jdbcStrategy = new JDBCStrategy(view.getLogin(), tableName);

		jdbcStrategy.writeSongList(list);
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
