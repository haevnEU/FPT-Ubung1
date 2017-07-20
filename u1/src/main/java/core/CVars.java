package core;
import javafx.application.Platform;

import static java.lang.System.exit;

/**
 * This class provides console variables
 * <p>
 * Created by Nils Milewsi (nimile) on 17.07.17
 */
public final class CVars {
	private static String SERVER_IP = "localhost", NAME = "IModel";
	private static int LOGIN_PORT = 5020, TIME_PORT = 5000, RMI_PORT = 0;
	private static boolean isClient = false;
	public static void setStaticVariable(String command) {
		String variable = "";
		String value = "";
		try {
			if(command.length() < 0) return;
			if(!command.contains(":")) command+=":";
			variable = command.substring(0, command.indexOf(":"));
			value = command.substring(command.indexOf(":") + 1, command.length());
			}catch(Exception ex){
			ex.printStackTrace();
		}

		switch (variable.toUpperCase()) {
			case "LOGINPORT":
				LOGIN_PORT = Integer.parseInt(value);
				break;

			case "TIMEPORT":
				TIME_PORT = Integer.parseInt(value);
				break;

			case "RMIPORT":
				RMI_PORT = Integer.parseInt(value);
				break;

			case "SERVERIP":
				SERVER_IP = value;
				break;
			case "EXIT":
				Platform.exit();
				exit(0);
				break;
			default:
				System.out.println("Entered: " + command);
				System.out.println("Invalid CVar, usage <CVar>:<new value>, " +
						"possible commands: LOGINPORT:5020, TIMEPORT:5000, RMIPORT:1099, SERVERIP:localhost, EXIT:0");
				break;
		}
	}

	public static int getLoginPort() {
		return LOGIN_PORT;
	}

	public static int getRmiPort() {
		return RMI_PORT;
	}

	public static int getTimePort() {
		return TIME_PORT;
	}

	public static String getServerIp() {
		return SERVER_IP;
	}

	public static void setIsClientEnabled(){
		isClient = true;
	}

	public static boolean isClientEnabled() {
		return isClient;
	}

	public static void setRMIName(String name){
		NAME = name;
	}

	public static String getRMIName() {
		return NAME;
	}
}
