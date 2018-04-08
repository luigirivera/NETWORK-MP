package server.model;

import java.util.List;

import message.Message;
import server.view.ServerMessageObserver;

public interface ServerMessageModel {
	public void updateAll();
	public void attach(ServerMessageObserver obs);
	public void detach(ServerMessageObserver obs);
	public List<Message<?>> getAll();
	public void add(Message<?> message);
	public void clear();
}
