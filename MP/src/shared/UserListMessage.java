package shared;

import java.util.List;

public class UserListMessage extends Message {
	private static final long serialVersionUID = 1L;
	private List<String> usernames;

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

}
