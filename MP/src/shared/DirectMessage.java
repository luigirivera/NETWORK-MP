package shared;

public class DirectMessage extends Message {
	private static final long serialVersionUID = 1L;
	private String receiver;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
