package shared;

public class DirectMessage extends Message {
	private static final long serialVersionUID = 1L;
	private String recipient;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
