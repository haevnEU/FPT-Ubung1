package server;

import core.Util;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class provides
 * <p>
 * Created by Nils Milewsi (nimile) on 12.07.17
 */
public class OwnSyncArrayList {

	Map<Long, String> log= new HashMap<>();
	String value;
	TextArea tbLog;
	int pointer = 0;
	public OwnSyncArrayList(TextArea tbLog){
		this.tbLog = tbLog;
	}

	public  void add(String value){
		System.out.println(value);
	}

	public synchronized void add(String value, int i){
		if(pointer >= 1){
			Platform.runLater(() -> {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				tbLog.setText(tbLog.getText() + "\n" + value + " TIME: " + dateFormat.format(date) );
				tbLog.appendText("");
			clear();
			add(value);});
			return;
		}
		this.value = value;
		log.put(Util.getUnixTimeStamp(), value);
		pointer++;
	}

	public synchronized void clear(){
		value = null;
		pointer = 0;
	}

	public Map<Long, String> getLog(){ return log;}
}
