package controller;

import core.*;
import interfaces.*;
import view.DetailView;

import java.rmi.RemoteException;

public class DetailController implements interfaces.IController {

    private Model model;
    private DetailView view;
    private Song song;
    /**
     * This method links the application model with the DetailView
     * @param m Model which should be used
     * @param v MainView.MainView which should be used
     */
    @Override
    public void link(IModel m, IView v){

        this.model = (Model)m;
        this.view = (DetailView)v;
      }


    public void init(Song song){
        this.song = song;
        view.initUI(song);
        this.view.addButtonCommitEventHandler(e -> buttonCommitClickEventHandler());
    }

    /**
     * Handles commit button
     */
    private void buttonCommitClickEventHandler(){
        try {
            song.setTitle(view.getTitle());
            song.setInterpret(view.getInterpret());
            song.setAlbum(view.getAlbumName());
            if(CVars.isClientEnabled()) try {
                model.notifySongChange(new SongWrapper(song));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("[WARN] Index out of bound occurred at " + Util.getUnixTimeStamp());
            ex.printStackTrace(System.out);
        }
    }
}
