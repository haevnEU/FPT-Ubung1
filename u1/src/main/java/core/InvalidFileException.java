package core;

/**
 * This class is used for handling own file exceptions
 */
public class InvalidFileException extends Exception {

    // normal ctor
    public InvalidFileException(){
        super("InvalidFileOperation occurred");
        // Display an exception box to our user
        util.showExceptionMessage(this);
    }
}
