package interfaces;


/**
 * This interface is used for future player objects
 *
 * written by Nils Milewski (nimile)
 */
public interface IPlayer {

	/**
	 * Plays a track, required initialization with init call
	 */
	void play();

	/**
	 * Stops the current track, required initialization with init call
	 */
	void stop();

	/**
	 * Pauses the current track, required initialization with init call
	 */
	void pause();

	/**
	 * Plays the next track, required initialization with init call
	 */
	void skip();
}
