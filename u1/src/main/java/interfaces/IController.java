package interfaces;

/**
 * This abstract class is used as a skeleton for every core.controller
 */
public interface IController {

    /**
     * link model with view
     * @param m Application model
     * @param v MainView.MainView to link
     */
    public abstract void link(IModel m, IView v);
}
