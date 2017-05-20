package deleteView;

import core.Model;
import interfaces.*;

import java.rmi.RemoteException;

/**
 * This class provides the controller for deleteView
 */
public class DeleteController extends IController{


    private DeleteView view;
    public void link(Model m, IView v){

        this.model = m;
        this.view = (DeleteView)v;

        this.view.addButtonDeleteEventHandler(e -> btRemoveClickEventHandler());
        this.view.addButtonDeleteAllEventHandler(e->btRemoveAllEventHandler());
    }

	/**
	 * Removes every entry in queue
	 */
	private void btRemoveAllEventHandler() {
	    try {
		    model.getQueue().remove(0, model.getQueue().sizeOfList());
	    } catch (RemoteException ex) {
	    	ex.printStackTrace();
	    }
    }

    /**
     * Removes selected entry in queue
     */
    private void btRemoveClickEventHandler(){
        int deleteIndex = view.getSelectedIndex();
        if(deleteIndex >= 0) model.getQueue().remove(deleteIndex);
    }
}


