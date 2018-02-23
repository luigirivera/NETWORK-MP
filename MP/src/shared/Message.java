package shared;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String sender;
	protected String message;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
