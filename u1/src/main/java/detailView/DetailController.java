package detailView;

import interfaces.IView;

public class DetailController extends interfaces.IController {
    private core.Model model;
    private DetailView view;


    public boolean init(){



        return true;
    }

    /**
     * This method links the application model with the DetailView
     * @param m Model which should be used
     * @param v View which should be used
     */
    @Override
    public void link(core.Model m, IView v){

        this.model = m;
        this.view = (DetailView)v;

        // Apply click event
        this.view.addButtonCommitEventHandler(e -> buttonCommitClickEventHandler());
    }


    // This method allows buttonCommit event handling !INTERNAL USAGE!
    private void buttonCommitClickEventHandler(){

        model.getQueue().get(0).setTitle(view.getTitle());
        model.getQueue().get(0).setInterpret(view.getInterpret());
        model.getQueue().get(0).setAlbum(view.getAlbumName());

        // close Detail window
        view.closeView();
    }

}
