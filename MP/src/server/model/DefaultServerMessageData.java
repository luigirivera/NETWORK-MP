package server.model;

import java.util.ArrayList;
import java.util.List;

import message.Message;

public class DefaultServerMessageData implements ServerMessageData {
	private List<Message<?>> messages;
	
	public DefaultServerMessageData() {
		this.messages = new ArrayList<Message<?>>();
	}

	@Override
	public List<Message<?>> getAll() {
		return this.messages;
	}

	@Override
	public void add(Message<?> message) {
		this.messages.add(message);
	}

	@Override
	public void clear() {
		this.messages.clear();
	}
	
}
