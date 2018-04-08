package message;

import java.io.Serializable;
import java.time.LocalDateTime;

import message.utility.MessageScope;

public abstract class Message<E> implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String source;
	protected String destination;
	protected E content;
	protected MessageScope scope;
	protected LocalDateTime timestamp;
	
	protected Message() {}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public E getContent() {
		return content;
	}

	public void setContent(E content) {
		this.content = content;
	}

	public MessageScope getScope() {
		return scope;
	}

	public void setScope(MessageScope scope) {
		this.scope = scope;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}