package shared;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String sender;
	protected String content;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
