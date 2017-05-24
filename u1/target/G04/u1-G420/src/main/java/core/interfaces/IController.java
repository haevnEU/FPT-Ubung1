package core.interfaces;

import core.util.Model;


/**
 * This abstract class is used as a skeleton for every core.controller
 */
public abstract class IController {

    protected Model model;

    /**
     * link model with core.view
     * @param m Application model
     * @param v View.View to link
     */
    public abstract void link(Model m, IView v);
}
