package ApplicationException;

import interfaces.IApplicationException;

/**
 * This class provides ID overflow exceptions
 * A IDOverFlowException occurred if the id reached the maximum value (9999)
 */
public final class IDOverFlowException extends Exception implements IApplicationException {

	public IDOverFlowException(){
		super();
	}

	public IDOverFlowException(String msg){
		super(msg);
	}
}
