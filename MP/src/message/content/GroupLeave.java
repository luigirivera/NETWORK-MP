package message.content;

import java.io.Serializable;

public class GroupLeave implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	public GroupLeave(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
