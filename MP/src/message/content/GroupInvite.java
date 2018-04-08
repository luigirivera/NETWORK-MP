package message.content;

import java.io.Serializable;

public class GroupInvite implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String username;

	public GroupInvite(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
