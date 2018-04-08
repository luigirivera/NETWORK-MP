package message.content;

import java.io.Serializable;

public class GroupJoin implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String password;

	public GroupJoin(String id) {
		this(id, "");
	}

	public GroupJoin(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
