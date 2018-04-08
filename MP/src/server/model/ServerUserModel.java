package server.model;

import java.util.List;

import message.content.UsernameList;
import server.view.ServerUserObserver;

public interface ServerUserModel {
	public void updateAll();
	public void attach(ServerUserObserver obs);
	public void detach(ServerUserObserver obs);
	public List<UserConnection> getAll();
	public UserConnection get(String name);
	public UsernameList getNames();
	public void add(UserConnection user);
	public void remove(UserConnection user);
}
