package core;


import interfaces.ISong;

import java.util.*;
import javafx.scene.media.*;
import javafx.beans.property.*;

import javafx.util.Duration;
import javafx.beans.value.ChangeListener;

/**
 * This class provides playable functionality
 */
public class Player extends Observable implements interfaces.IPlayer{

    private boolean playing = false;
    private MediaPlayer mediaPlayer;
    private Media media;
    private Song currentSong;

    // Singleton usage because there should never exists two player
    private static Player instance;
    private Player(){}
    static Player getInstance(){
        if(instance == null) instance = new Player();
        return instance;

    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Initialize the media object
     * @param song the current which should played
     */
    public void init(Song song) {
        currentSong = song;
        media = new Media(currentSong.pathProperty().getValue());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> endOfMedia());
        mediaPlayer.setOnPaused(() -> setOnPaused());

    }

    /**
     * Set the end of media method
     */
    private void endOfMedia() {
        mediaPlayer.stop();
          setChanged();
        notifyObservers();
    }

    /**
     * Set what should happened if the player is paused
     */
    private void setOnPaused(){

    }

    /**
     * Set what should happened if the player is playing
     */
    public void setOnPlaying(Runnable value){
        mediaPlayer.setOnPlaying(value);
    }

    /**
     * Set what should happened if the media player is ready
     * @param value
     */
    public void setOnReady(Runnable value) {
        mediaPlayer.setOnReady(value);
    }

    /**
     * Plays a track, required initialization with init call
     */
    public void play(){
        if(!playing){
            mediaPlayer.play();
            playing = true;
        }
    }

    /**
     * Stops the current track, required initialization with init call
     */
    public void stop(){
        if(playing) {
            mediaPlayer.stop();
            playing = false;
        }
    }

    /**
     * Pauses the current track, required initialization with init call
     */
    public void pause(){
        if(playing) {
            mediaPlayer.pause();
            playing = false;
        }
    }

    /**
     * Plays the next track, required initialization with init call
     */
    public void skip(ISong nextSong){
        mediaPlayer.pause();
        mediaPlayer.stop();
        nextSong((Song)nextSong);
        mediaPlayer.play();
    }

    /**
     * Sets the next playing song
     * @param nextSong next song which should be played
     */
    public void nextSong(Song nextSong) {
        mediaPlayer = null;
        mediaPlayer = new MediaPlayer(new Media(nextSong.pathProperty().getValue()));
    }



    /**
     * Access track length
     * @return total length of current track
     */
    public double getLength() {
        return mediaPlayer.getCurrentTime().toSeconds();

    }

    /**
     * Access volume property of media player
     * @return current volume property
     */
    public DoubleProperty volumeProperty(){
        return mediaPlayer.volumeProperty();
    }

    /**
     * Adds a time change event handler
     * @param e method which should be invoked
     */
    void addTimeChangeListener(ChangeListener<Duration> e){
        mediaPlayer.currentTimeProperty().addListener(e);
    }

}