package controller;

import interfaces.*;

import core.Model;
import view.DetailView;
import java.util.Observable;

/**
 * This class provides the controller functionality for the detail view
 */
public class DetailController implements interfaces.IController {

    Model model;
    DetailView view;

    @Override
    public void link(IModel m, IView view) {
        this.model = (Model) m;
        this.view = (DetailView) view;
    }

    @Override
    public void update(Observable o, Object arg) {}
}
