package applicationException;

import interfaces.IApplicationException;

/**
 * This class should be used as default, if there is no existing exception
 *
 * Written by Nils, Milewski (nimile)
 */
public class UnknownApplicationException extends Exception implements IApplicationException {

	/**
	 * Creates a new UAE
	 */
	public UnknownApplicationException(){
		super();
	}

	/**
	 * Creates a new UAE based on a message
	 * @param msg Message which should be displayed
	 */
	public UnknownApplicationException(String msg){
		super(msg);
	}
}
