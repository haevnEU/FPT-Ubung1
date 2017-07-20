package interfaces;

/**
 * This interface is used for future controller
 *
 * written by Nils Milewski (nimile)
 */
public interface IController {

    /**
     * link model with view
     * @param m Application model
     * @param v MainView.MainView to link
     */
    void link(IModel m, IView v);
    
}
