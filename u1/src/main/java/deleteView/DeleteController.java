package deleteView;

import core.Model;
import interfaces.IController;
import interfaces.IView;
import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the controller for deleteView
 */
public class DeleteController extends IController{


    private DeleteView view;
    public void link(Model m, IView v){

        this.model = m;

        this.view = (DeleteView)v;

        locale = core.util.load(getClass().getName());
        this.view.setLocale(locale);

        this.view.addButtonDeleteEventHandler(e -> btRemoveClickEventHandler(e));
    }

    /**
     * Attach event to button remove
     * @param e
     */
    public void btRemoveClickEventHandler(ActionEvent e){
        int deleteIndex = view.getSelectedIndex();
        if(deleteIndex >= 0) model.getQueue().remove(deleteIndex);
    }


}


