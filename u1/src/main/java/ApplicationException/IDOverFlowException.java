package applicationException;

import interfaces.IApplicationException;

/**
 * This class provides ID overflow exceptions
 * A IDOverFlowException occurred if the id reached the maximum value (9999)
 *
 * Written by Nils Milewski (nimile)
 */
public final class IDOverFlowException extends Exception implements IApplicationException {

	/**
	 * Empty IDOverflow exception
	 */
	public IDOverFlowException(){
		super();
	}

	/**
	 * IDOverflow exception based on a message
	 * @param msg Message which should be displayed
	 */
	public IDOverFlowException(String msg){
		super(msg);
	}
}
