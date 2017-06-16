package core;

import interfaces.ISong;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.rmi.RemoteException;

/**
 * This class provides playable logic
 */
class Player implements interfaces.IPlayer {

	private MediaPlayer mediaPlayer;
	private Media media;
	private SongList queue;
	private boolean isInitialized = false;
	private SimpleBooleanProperty isPlaying = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty endOfTrack = new SimpleBooleanProperty(false);
	private ChangeListener mediaPlayerChangeTime;
	private Song nextSong;
	// Singleton usage because there should never exists two player
	private static Player instance;
	private Player(){}

	/**
	 * Get MediaPlayer object
	 * @return new MediaPlayer if there exists no one, otherwise existing MediaPlayer
	 */
	static Player getInstance(){
		if(instance == null) instance = new Player();
		return instance;
	}

	/**
	 * Initialize the MediaPlayer
	 * @param s ISong which should be played
	 */
	private void initPlayer(Song s){
		// Set or change media
		media = new Media(s.getPath());

		// Initialize or reinitialize MediaPlayer
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(() -> endOfMedia());
		mediaPlayer.setOnPlaying(() -> setOnPlaying());
		mediaPlayer.setOnPaused(() -> setOnPaused());
	}

	/**
	 * Initialize the media object
	 * @param songs the queue which should played
	 */
	public void init(SongList songs){
		System.out.println("Initialize MediaPlayer object");
		// return if the mediaPlayer is already initialized
		if(isInitialized) return;
		queue = songs;
		initPlayer((Song)songs.get(0));

		isInitialized = true;
		System.out.println("Initialized new state: " + isInitialized);
	}

	/**
	 * Attach event handling for EOF
	 * @param e Method which should be called
	 */
	public void addEndOfMediaListener(ChangeListener<Boolean> e){
		endOfTrack.addListener(e);
	}

	/**
	 * Attach event handling for TimeChange event
	 * @param e Method which should be called
	 */
	void addTimeChangeListener(ChangeListener<Duration> e){
	    mediaPlayerChangeTime = e;
		mediaPlayer.currentTimeProperty().addListener(e);
	}

	/**
	 * Set the end of media method
	 */
	private void endOfMedia() {
		try {
			System.out.println("Enter EndOfMedia");
			// return if queue has no elements
			if(queue.sizeOfList() <= 0) return;
			// reset media
			media = null;
			stop();
			// remove current song from queue
			queue.remove(0);
			initPlayer((Song)queue.get(0));
			mediaPlayer.currentTimeProperty().addListener(mediaPlayerChangeTime);
			play();
			System.out.println("Successfully leaved EndOfMedia");
		}
		catch (RemoteException ex) {
			System.err.println("[CRIT] ApplicationException occurred at " + Util.getUnixTimeStamp());
			ex.printStackTrace();
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
	}



	/**
	 * Plays a track, required initialization with init call
	 */
	public void play(){
		// test if player is initialized
		if(isInitialized) mediaPlayer.play();
	}

	/**
	 * Stops the current track, required initialization with init call
	 */
	public void stop(){
		if(isInitialized) mediaPlayer.stop();
	}

	/**
	 * Pauses the current track, required initialization with init call
	 */
	public void pause(){
		if(isInitialized) mediaPlayer.pause();
	}


	/**
	 * Plays the next track, required initialization with init call
	 * Skip forces end of current track
	 */
	public void skip(){
		if(isInitialized) endOfMedia();
	}

	/**
	 * Get playing state
	 * @return state of isPlaying
	 */
	SimpleBooleanProperty getIsPlaying(){ return isPlaying; }

	/**
	 * Get the song length, note beta state
	 * @return song length as seconds
	 */
	public double getSongLength() {
		if(isInitialized) return media.getDuration().toSeconds();
		return 0;
	}

	public boolean getInitialized() {
		return isInitialized;
	}
}
