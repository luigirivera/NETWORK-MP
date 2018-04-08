package server.model;

import java.util.ArrayList;
import java.util.List;

import server.view.ServerGroupObserver;

public class DefaultServerGroupModel implements ServerGroupModel {
	private ServerGroupData data;
	private List<ServerGroupObserver> observers;
	private List<ServerGroupObserver> toAdd;
	private List<ServerGroupObserver> toRemove;

	public DefaultServerGroupModel(ServerGroupData data) {
		this.data = data;
		this.observers = new ArrayList<ServerGroupObserver>();
		this.toAdd = new ArrayList<ServerGroupObserver>();
		this.toRemove = new ArrayList<ServerGroupObserver>();
	}

	@Override
	public void updateAll() {
		this.observers.addAll(toAdd);
		toAdd.clear();
		this.observers.removeAll(toRemove);
		toRemove.clear();
		for (ServerGroupObserver obs : observers)
			obs.updateGroups();
	}

	@Override
	public void attach(ServerGroupObserver obs) {
		this.toAdd.add(obs);
	}

	@Override
	public void detach(ServerGroupObserver obs) {
		this.toRemove.add(obs);
	}

	@Override
	public List<ChatGroup> getAll() {
		return data.getAll();
	}

	@Override
	public List<PrivateChatGroup> getPrivate() {
		return data.getPrivate();
	}

	@Override
	public List<PublicChatGroup> getPublic() {
		return data.getPublic();
	}

	@Override
	public ChatGroup get(String id) {
		return data.get(id);
	}

	@Override
	public void addUserToGroup(UserConnection user, ChatGroup group) {
		data.addUserToGroup(user, group);
		this.updateAll();
	}

	@Override
	public void removeUserFromGroup(UserConnection user, ChatGroup group) {
		data.removeUserFromGroup(user, group);
		this.updateAll();
	}

	@Override
	public void removeUserFromAll(UserConnection user) {
		data.removeUserFromAll(user);
		this.updateAll();
	}

	@Override
	public void add(ChatGroup group) {
		data.add(group);
		this.updateAll();
	}

	@Override
	public void remove(ChatGroup group) {
		data.remove(group);
		this.updateAll();
	}

}
