package ApplicationException;

/**
 * This class should be used as default, if there is no existing exception
 */
public class UnknownApplicationException extends Exception implements interfaces.IApplicationException {

	public UnknownApplicationException(){
		super();
	}

	public UnknownApplicationException(String msg){
		super(msg);
	}
}
