package core.controller;

import core.view.DetailView;
import core.interfaces.IView;

public class DetailController extends core.interfaces.IController {
    private core.util.Model model;
    private DetailView view;


    /**
     * This method links the application model with the DetailView
     * @param m Model which should be used
     * @param v View.View which should be used
     */
    @Override
    public void link(core.util.Model m, IView v){

        this.model = m;
        this.view = (DetailView)v;

        // Apply click event
        this.view.addButtonCommitEventHandler(e -> buttonCommitClickEventHandler());
    }

    /**
     * Handles commit button
     */
    private void buttonCommitClickEventHandler(){

        try {
            model.getQueue().get(0).setTitle(view.getTitle());
            model.getQueue().get(0).setInterpret(view.getInterpret());
            model.getQueue().get(0).setAlbum(view.getAlbumName());
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        // close Detail window
        view.closeView();
    }

}
