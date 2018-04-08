package server.model;

import java.util.ArrayList;
import java.util.List;

import message.content.UsernameList;

public class DefaultServerUserData implements ServerUserData {
	private List<UserConnection> users;

	public DefaultServerUserData() {
		this.users = new ArrayList<UserConnection>();
	}

	@Override
	public List<UserConnection> getAll() {
		return this.users;
	}

	@Override
	public UserConnection get(String name) {
		for (UserConnection uc : users) {
			if (uc.getUser().getName().equalsIgnoreCase(name))
				return uc;
		}
		return null;
	}
	
	@Override
	public UsernameList getNames() {
		UsernameList list = new UsernameList();
		for (UserConnection uc : users) {
			list.add(uc.getUser().getName());
		}
		return list;
	}

	@Override
	public void add(UserConnection user) {
		users.add(user);
	}

	@Override
	public void remove(UserConnection user) {
		users.remove(user);
	}

}
