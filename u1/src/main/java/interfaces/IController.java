package interfaces;

import java.util.Observer;

/**
 * This interface is a blueprint for new controller
 */
public interface IController extends Observer{

    /**
     * Link the model with a view
     * <p>Both the model and the view must implement each interface</p>
     * @param m Model which should be used for linking process
     * @param view View which should be used for linking process
     */
    public void link(IModel m, IView view);

}
