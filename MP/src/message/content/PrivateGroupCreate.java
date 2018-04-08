package message.content;

import java.io.Serializable;
import java.util.List;

public class PrivateGroupCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> usernames;

	public PrivateGroupCreate(List<String> usernames) {
		this.usernames = usernames;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

}
