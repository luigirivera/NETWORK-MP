package server.model;

import java.util.ArrayList;
import java.util.List;

import message.Message;
import server.view.ServerMessageObserver;

public class DefaultServerMessageModel implements ServerMessageModel {
	private ServerMessageData messages;
	private List<ServerMessageObserver> observers;
	private List<ServerMessageObserver> toAdd;
	private List<ServerMessageObserver> toRemove;
	
	public DefaultServerMessageModel(ServerMessageData messages) {
		this.messages = messages;
		this.observers = new ArrayList<ServerMessageObserver>();
		this.toAdd = new ArrayList<ServerMessageObserver>();
		this.toRemove = new ArrayList<ServerMessageObserver>();
	}

	@Override
	public void updateAll() {
		this.observers.addAll(toAdd);
		toAdd.clear();
		this.observers.removeAll(toRemove);
		toRemove.clear();
		for (ServerMessageObserver obs : observers) {
			obs.updateMessages();
		}
		this.clear();
	}

	@Override
	public void attach(ServerMessageObserver obs) {
		this.toAdd.add(obs);
	}

	@Override
	public void detach(ServerMessageObserver obs) {
		this.toRemove.add(obs);
	}

	@Override
	public List<Message<?>> getAll() {
		return messages.getAll();
	}

	@Override
	public void add(Message<?> message) {
		messages.add(message);
		this.updateAll();
	}

	@Override
	public void clear() {
		messages.clear();
	}
	
}
