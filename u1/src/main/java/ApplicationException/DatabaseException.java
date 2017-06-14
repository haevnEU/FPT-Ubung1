package ApplicationException;

import java.io.IOException;

/**
 * This class provides database exception handling
 *
 * written by Nils Milewski (nimile)
 */
public final class DatabaseException extends Exception implements interfaces.IApplicationException {

	/**
	 * Empty database exception
	 */
	public DatabaseException(){
		super();
	}

	/**
	 * Exception based on a message
	 * @param msg Message which should be displayed
	 */
	public DatabaseException(String msg){
		super(msg);
	}

	/**
	 * Exception based on an another exception
	 * @param ex based exception
	 */
	public DatabaseException(IOException ex) {
		super(ex);
	}
}