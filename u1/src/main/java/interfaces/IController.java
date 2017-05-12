package interfaces;

import core.Model;

import java.util.HashMap;
import java.util.Map;


/**
 * This abstract class is used as a skeleton for every controller
 */
public abstract class IController {

    protected Model model;
    protected IView view;
    protected Map<String, String> locale = new HashMap<>();

    /**
     * link model with view
     * @param m Application model
     * @param v View to link
     */
    public abstract void link(Model m, IView v);
}
