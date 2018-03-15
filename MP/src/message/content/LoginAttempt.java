package message.content;

import java.io.Serializable;

public class LoginAttempt implements Serializable {
	private static final long serialVersionUID = 1L;
	String username;
	//String password;
	//boolean newUser;
	
	public LoginAttempt(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}*/

}
