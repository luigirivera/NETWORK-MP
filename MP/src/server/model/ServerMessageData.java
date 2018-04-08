package server.model;

import java.util.List;

import message.Message;

public interface ServerMessageData {
	public List<Message<?>> getAll();
	public void add(Message<?> message);
	public void clear();
}
