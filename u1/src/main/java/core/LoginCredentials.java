package core;

import ApplicationException.DatabaseException;

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
		if(username.contains(";") || pw.contains(";"))throw new DatabaseException("SQL INJECTION DETECTED!");
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
