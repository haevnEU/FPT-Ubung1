package controller;

import core.Song;
import interfaces.*;

import view.DetailView;

public class DetailController implements interfaces.IController {

    private core.Model model;
    private DetailView view;
    private Song song;

    /**
     * This method links the application model with the DetailView
     * @param m Model which should be used
     * @param v MainView.MainView which should be used
     */
    @Override
    public void link(IModel m, IView v){

        this.model = (core.Model)m;
        this.view = (DetailView)v;
        // Apply click event
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
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
