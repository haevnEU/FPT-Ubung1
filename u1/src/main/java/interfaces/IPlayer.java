package interfaces;


public interface IPlayer {
	/**
	 * Initialize the media object
	 * @param song the current which should played
	 */
	public void init(ISong song);

	/**
	 * Plays a track, required initialization with init call
	 */
	public void play();

	/**
	 * Stops the current track, required initialization with init call
	 */
	public void stop();

	/**
	 * Pauses the current track, required initialization with init call
	 */
	public void pause();

	/**
	 * Plays the next track, required initialization with init call
	 */
	public void skip(ISong nextSong);
}
