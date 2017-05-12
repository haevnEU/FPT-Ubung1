package detailView;

public class Controller {
    private core.Model model;
    private DetailView view;


    public void link(core.Model m, DetailView v){

        this.model = m;

        this.view = v;

//        view.tbInterpret.setText(model.getQueue().get(0).getInterpret());
//        view.tbTitle.setText(model.getQueue().get(0).getTitle());
//        view.tbAlbum.setText(model.getQueue().get(0).getAlbum());
//        view.lbBase.setText(view.lbBase.getText() + model.getQueue().get(0).getId());

        this.view.addButtonCommitEventHandler(e -> btControllerEventHandler(e));
    }


    public void btControllerEventHandler (javafx.event.ActionEvent e){

         //chance Model
//        model.getQueue().get(0).setAlbum(view.tbAlbum.getText());
//        model.getQueue().get(0).setInterpret(view.tbInterpret.getText());
//        model.getQueue().get(0).setTitle(view.tbTitle.getText());
//        // cose Detail window
//        Stage stage = (Stage) view.btCommit.getScene().getWindow();
//        stage.close();
    }

}
