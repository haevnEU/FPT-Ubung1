package core;

/**
 * This class hold login details for DB Access
 */
public final class LoginCredentials {
	private String username, pw;

	/**
	 * Creates a new instance of this class
	 * @param username Username which should be used
	 * @param pw Password which should be used
	 */
	public LoginCredentials(String username, String pw){
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
