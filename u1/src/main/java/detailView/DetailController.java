package detailView;

public class DetailController {
    private core.Model model;
    private DetailView view;


    /**
     * This method links the application model with the detailview
     * @param m
     * @param v
     */
    public void link(core.Model m, DetailView v){

        this.model = m;
        this.view = v;

//        view.tbInterpret.setText(model.getQueue().get(0).getInterpret());
//        view.tbTitle.setText(model.getQueue().get(0).getTitle());
//        view.tbAlbum.setText(model.getQueue().get(0).getAlbum());
//        view.lbBase.setText(view.lbBase.getText() + model.getQueue().get(0).getId());

        // Apply click event
        this.view.addButtonCommitEventHandler(e -> buttonCommitClickEventHandler(e));
    }


    // This method allows buttonCommit event handling !INTERNAL USAGE!
    private void buttonCommitClickEventHandler(javafx.event.ActionEvent e){

//        TODO Implement functionality

//        model.getQueue().get(0).setAlbum(view.tbAlbum.getText());
//        model.getQueue().get(0).setInterpret(view.tbInterpret.getText());
//        model.getQueue().get(0).setTitle(view.tbTitle.getText());

        // close Detail window
        view.closeView();
    }

}
