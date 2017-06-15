package core;

import ApplicationException.DatabaseException;
import javafx.scene.control.Alert;

/**
 * This class hold login details for DB Access
 *
 * Written by Nils Milewski (nimile)
 */
public final class LoginCredentials {
	private String username, pw;

	/**
	 * Creates a new instance of this class
	 * @param username Username which should be used
	 * @param pw Password which should be used
	 *@throws DatabaseException throws DBException if a SQL Injection is detected inside this credential
	 */
	public LoginCredentials(String username, String pw) throws DatabaseException {
		if(username.contains(";") || pw.contains(";")){
			Util.showAlert("System Warning!\n" +
			"You are not allowed to access to any database using this login data.\n\n" +
			"If you are using this data you will cause an SQL Injection\n\n\n" +
			"Sorry dude but this is an violation against the database Why are you so evil?!:(", Alert.AlertType.ERROR);

			throw new DatabaseException("SQL INJECTION DETECTED!");
		}
		if("".equals(username) || "".equals(pw)){
			Util.showAlert("Your login credential is empty\n\n" +
					"This access could be denied,\n" +
					"if this is the case please enter Login data and try again", Alert.AlertType.INFORMATION);
		}
		this.username = username;
		this.pw = pw;
	}

	/**
	 * Get the Password
	 * @return user password
	 */
	public String getPw() {
		return pw;
	}

	/**
	 * Get the username
	 * @return name of the user
	 */
	public String getUsername() {
		return username;
	}
}
