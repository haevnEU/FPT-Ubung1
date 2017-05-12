package localization;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nilsmilewski on 09.05.17.
 */
public class LoadLocale {
    private Map<String, String> object;

    public LoadLocale(){
        object = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader("locale.lc"))){

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }
        }
        catch(IOException e){

        }
    }

    public String getText(String key){
        return object.get(key);
    }
}
