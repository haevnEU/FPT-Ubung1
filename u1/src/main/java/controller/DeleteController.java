package controller;

import interfaces.*;

import core.Model;
import javafx.scene.input.MouseEvent;
import view.DeleteView;

/**
 * This class provides functionality for the delete view
 *
 * written by Nils Milewski (nimile)
 */
public class DeleteController implements IController{

    private DeleteView view;
    private Model model;

    @Override
    public void link(IModel m, IView v){

        this.model = (Model)m;
        this.view = (DeleteView)v;

        this.view.addButtonDeleteEventHandler(e -> btRemoveClickEventHandler());
        this.view.addButtonDeleteAllEventHandler(e -> btRemoveAllEventHandler());
        this.view.addListViewClickedEventHandler(this::lvItemsClicked);
    }

	private void lvItemsClicked(MouseEvent e) {
        if(e.getClickCount() == 2) btRemoveClickEventHandler();
    }

    /**
	 * Removes every entry in queue
	 */
	private void btRemoveAllEventHandler() {
		model.getQueue().removeAll(model.getQueue());
	}

    /**
     * Removes selected entry in queue
     */
    private void btRemoveClickEventHandler(){
        int deleteIndex = view.getSelectedIndex();
        if(deleteIndex >= 0) model.getQueue().remove(deleteIndex);
    }
}