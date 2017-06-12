package controller;


import core.JDBCStrategy;
import core.LoginCredentials;
import core.Model;
import interfaces.IDatabaseUtils;
import interfaces.IModel;
import interfaces.IView;
import javafx.stage.DirectoryChooser;
import view.LoadFiles;
import view.SaveView;

import java.io.IOException;

public class SaveController implements interfaces.IController {

	SaveView view;
	Model model;

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
		JDBCStrategy jdbcStrategy = new JDBCStrategy(view.getLogin(), model.getQueue(), "Playlist");
		try {
			jdbcStrategy.writeSong(model.getQueue().get(0));
		} catch (IOException e) {
			e.printStackTrace();
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
