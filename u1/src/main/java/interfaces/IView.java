package interfaces;

/**
 * This interface is a blueprint for every view
 */
public interface IView {

    /**
     * Initialize routine
     * <b>This routine must be called</b>
     */
    public void init();

    /**
     * Release the instance object
     */
    public void destroy();
}

