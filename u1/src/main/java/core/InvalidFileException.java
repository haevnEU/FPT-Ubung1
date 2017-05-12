package core;

public class InvalidFileException extends Exception {
    public InvalidFileException(){

        super("InvalidFileOperation occured");

        util.showExceptionMessage(this);//, super.getMessage(), sw.toString());
    }





}
