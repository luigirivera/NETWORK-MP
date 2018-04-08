package server.model;

import java.util.ArrayList;
import java.util.List;

import message.content.UsernameList;
import server.view.ServerUserObserver;

public class DefaultServerUserModel implements ServerUserModel {
	private ServerUserData users;
	private List<ServerUserObserver> observers;
	private List<ServerUserObserver> toAdd;
	private List<ServerUserObserver> toRemove;

	public DefaultServerUserModel(ServerUserData users) {
		this.users = users;
		this.observers = new ArrayList<ServerUserObserver>();
		this.toAdd = new ArrayList<ServerUserObserver>();
		this.toRemove = new ArrayList<ServerUserObserver>();
	}

	@Override
	public void updateAll() {
		observers.addAll(toAdd);
		toAdd.clear();
		observers.removeAll(toRemove);
		toRemove.clear();
		for (ServerUserObserver obs : observers) {
			obs.updateUsers();
		}
	}

	@Override
	public void attach(ServerUserObserver obs) {
		toAdd.add(obs);
	}

	@Override
	public void detach(ServerUserObserver obs) {
		toRemove.add(obs);
	}

	@Override
	public List<UserConnection> getAll() {
		return this.users.getAll();
	}

	@Override
	public UserConnection get(String name) {
		return this.users.get(name);
	}

	@Override
	public UsernameList getNames() {
		return this.users.getNames();
	}

	@Override
	public void add(UserConnection user) {
		this.users.add(user);
		this.updateAll();
	}

	@Override
	public void remove(UserConnection user) {
		this.users.remove(user);
		this.updateAll();
	}

}
