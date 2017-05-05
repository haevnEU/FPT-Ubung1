import javafx.event.ActionEvent;

/**
 * Created by nilsmilewski on 05.05.17.
 */
public class Controller {

    private Model model;
    private View view;


    public void actionPerformed(ActionEvent arg0) {
      //model. ...;
    }

    public void link(Model m, View v){

        this.model = m;


        this.view = v;

    }

}
