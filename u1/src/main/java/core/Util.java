package core;

import java.io.*;

public final class Util {

    /**
     * new instances are forbidden
     */
    private Util(){}
    /**
     * Returns timestamp based on unix time
     * @return current timestamp
     */
    public static Long getUnixTimeStamp(){
        return System.currentTimeMillis() / 1000L;
    }


    /**
     * Reads a file from disk
     * <i>could be used later</i>
     * @param path path to file
     * @return return file content
     * @throws IOException ...
     */
    public static String readFile(String path) throws IOException{
        String line = "";
        if(!new File(path).exists()) throw new FileNotFoundException();
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String inLine;
            while((inLine = br.readLine()) != null){
                line += inLine +" \n";
            }
        }
        return line;
    }

    /**
     * Invoke a new RichException
     * @param ex exception details
     */
    // TODO replace with direct throwing
    public static void showExceptionMessage(Exception ex) {
       new ApplicationException.RichException(ex);
    }
}














// QA
// Q: what is a stacktrace?
// A: a stacktrace is a stack which traces?
// ...
// ...
// Well a stacktrace shows the last executed method in a program
// it can help to locate possible errors, if you look at a stacktrace you now what i mean
// bellow is a stacktrace, but its modified
// we can see the first execution, the entry point and the last where the exception occurred
//java.lang.NullPointerException <-- Description of the exception
//        at MainController.MainController.menuItemLoadEventHandler(MainController.MainController.java:118) <-- occurrence of exception
//        at MainController.MainController.lambda$link$0(MainController.MainController.java:34)
//        at com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:218)
//        at ....
//        at java.lang.Thread.run(Thread.java:748) <-- entry point
