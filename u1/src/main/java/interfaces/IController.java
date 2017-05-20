package interfaces;

import core.Model;


/**
 * This abstract class is used as a skeleton for every controller
 */
public abstract class IController {

    protected Model model;

    /**
     * link model with view
     * @param m Application model
     * @param v View to link
     */
    public abstract void link(Model m, IView v);
}
