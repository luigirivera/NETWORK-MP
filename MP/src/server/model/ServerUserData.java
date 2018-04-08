package server.model;

import java.util.List;

import message.content.UsernameList;

public interface ServerUserData {
	public List<UserConnection> getAll();
	public UserConnection get(String name);
	public UsernameList getNames();
	public void add(UserConnection user);
	public void remove(UserConnection user);
}
