package client.model;

import java.util.ArrayList;
import java.util.List;

import client.view.ClientDMView;
import client.view.ClientGroupView;
import client.view.ClientObserver;
import client.view.ClientRoomView;
import message.Message;

public class DefaultClientModel implements ClientModel {
	private String username;
	private ClientData data;
	private List<ClientObserver> observers;
	private List<ClientObserver> toAdd;
	private List<ClientObserver> toRemove;

	public DefaultClientModel() {
		data = new DefaultClientData();
		observers = new ArrayList<ClientObserver>();
		toAdd = new ArrayList<ClientObserver>();
		toRemove = new ArrayList<ClientObserver>();
	}

	@Override
	public void updateAll() {
		observers.addAll(toAdd);
		toAdd.clear();
		observers.removeAll(toRemove);
		toRemove.clear();
		for (ClientObserver obs : observers)
			obs.update();
		this.clear();
	}

	@Override
	public void attach(ClientObserver obs) {
		toAdd.add(obs);
	}

	@Override
	public void detach(ClientObserver obs) {
		toRemove.add(obs);
	}

	@Override
	public boolean hasDMObserver(String dest) {
		observers.addAll(toAdd);
		toAdd.clear();
		observers.removeAll(toRemove);
		toRemove.clear();
		for (ClientObserver obs : observers) {
			if (obs instanceof ClientDMView && ((ClientDMView)obs).getDestination().equalsIgnoreCase(dest))
				return true;
		}
		return false;
	}

	@Override
	public boolean hasGroupObserver(String dest) {
		observers.addAll(toAdd);
		toAdd.clear();
		observers.removeAll(toRemove);
		toRemove.clear();
		for (ClientObserver obs : observers) {
			if (obs instanceof ClientGroupView && ((ClientGroupView)obs).getDestination().equalsIgnoreCase(dest))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasRoomObserver(String dest) {
		observers.addAll(toAdd);
		toAdd.clear();
		observers.removeAll(toRemove);
		toRemove.clear();
		for (ClientObserver obs : observers) {
			if (obs instanceof ClientRoomView && ((ClientRoomView)obs).getDestination().equalsIgnoreCase(dest))
				return true;
		}
		return false;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * @Override public List<Message<?>> getGlobal() { return data.getGlobal(); }
	 * 
	 * @Override public List<Message<?>> getDirect(String destination) { return
	 * data.getDirect(destination); }
	 * 
	 * @Override public List<Message<?>> getGroup(String destination) { return
	 * data.getGroup(destination); }
	 */

	@Override
	public Message<?> getLast() {
		return data.getLast();
	}

	@Override
	public void add(Message<?> message) {
		data.add(message);
		this.updateAll();
	}

	@Override
	public void clear() {
		data.clear();
	}

}
