package core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.rmi.RemoteException;


class Player {

	private MediaPlayer mediaPlayer;
	private Media media;
	private SongList queue;
	private boolean isInitialized = false;
	private SimpleBooleanProperty isPlaying = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty endOfTrack = new SimpleBooleanProperty(false);
	private SimpleDoubleProperty currentTime = new  SimpleDoubleProperty(0);

	// Singleton usage because there should never exists two player
	private static Player instance;
	private Player(){}
	static Player getInstance(){
		if(instance == null) instance = new Player();
		return instance;
	}

	/**
	 * Initialize the media object
	 * @param songs the queue which should played
	 */
	void init(SongList songs){
		if(!isInitialized){
			queue = songs;
			media = new Media(queue.get(0).getPath());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(() -> endOfMedia());
			mediaPlayer.setOnPlaying(() -> setOnPlaying());
			mediaPlayer.setOnPaused(() -> setOnPaused());
			isInitialized = true;
		}
	}

	void addEndOfMediaListener(ChangeListener<Boolean> e){
		endOfTrack.addListener(e);
	}

	void addTimeChangeListener(ChangeListener<Duration> e){
		mediaPlayer.currentTimeProperty().addListener(e);
	}

	/**
	 * Set the end of media method
	 */
	private void endOfMedia() {
		try {
			if(queue.sizeOfList() <= 0) return;

			queue.remove(0);
			mediaPlayer.stop();
			media = new Media(queue.get(0).getPath());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set what should happened if the player is paused
	 */
	private void setOnPaused(){
		isPlaying.set(false);
	}

	/**
	 * Set what should happened if the player is playing
	 */
	private void setOnPlaying(){
		isPlaying.set(true);
		currentTime.set(mediaPlayer.getCurrentTime().toSeconds());
		System.out.println(mediaPlayer.getCurrentTime().toSeconds());
	}


	/**
	 * Plays a track, required initialization with init call
	 */
	void play(){
		// test if player is initialized
		if(!isInitialized) return;
		mediaPlayer.play();
	}

	/**
	 * Stops the current track, required initialization with init call
	 */
	void stop(){
		if(!isInitialized) return;
		mediaPlayer.stop();
	}

	/**
	 * Pauses the current track, required initialization with init call
	 */
	void pause(){
		if(!isInitialized) return;
		mediaPlayer.pause();
	}

	/**
	 * Plays the next track, required initialization with init call
	 */
	void skip(){
		if(!isInitialized) return;
		endOfMedia();
	}

	SimpleBooleanProperty getIsPlaying(){ return isPlaying; }

	SimpleDoubleProperty getCurrentTime(){ return currentTime; }
}
