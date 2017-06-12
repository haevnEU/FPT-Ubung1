package ApplicationException;

/**
 * This class provides database exception handling
 */
public final class DatabaseException extends Exception implements interfaces.IApplicationException {

	public DatabaseException(){
		super();
	}

	public DatabaseException(String msg){
		super(msg);
	}
}