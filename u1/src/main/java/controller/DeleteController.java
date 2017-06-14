package controller;

import interfaces.*;

import core.Model;
import view.DeleteView;

public class DeleteController implements IController{

    private DeleteView view;
    private Model model;

    @Override
    public void link(IModel m, IView v){

        this.model = (Model)m;
        this.view = (DeleteView)v;

        this.view.addButtonDeleteEventHandler(e -> btRemoveClickEventHandler());
        this.view.addButtonDeleteAllEventHandler(e->btRemoveAllEventHandler());
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


