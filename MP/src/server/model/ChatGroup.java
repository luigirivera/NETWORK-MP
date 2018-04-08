package server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import message.Message;
import message.content.UsernameList;
import message.utility.MessageFactory;
import message.utility.MessageScope;

public abstract class ChatGroup {
	protected static int count = 0;
	protected String id;
	protected List<UserConnection> users;
	
	public ChatGroup() {
		this.id = String.valueOf(count);
		count++;
		this.users = new ArrayList<UserConnection>();
	}
	
	public String getId() {
		return id;
	}
	
	public List<UserConnection> getUsers() {
		return users;
	}
	
	public UsernameList getNames() {
		UsernameList list = new UsernameList();
		for (UserConnection uc : users) {
			list.add(uc.getUser().getName());
		}
		return list;
	}
	
	public boolean isEmpty() {
		return users.size()==0;
	}
	
	public void add(UserConnection uc) {
		if (!users.contains(uc)) {
			users.add(uc);
			try {
				this.blastUserList();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public void addAll(List<UserConnection> ucs) {
		for (UserConnection uc : ucs) {
			if (!users.contains(uc))
				users.add(uc);
		}
		try {
			this.blastUserList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public void remove(UserConnection uc) {
		if (users.remove(uc)) {
			try {
				this.blastUserList();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean contains(UserConnection uc) {
		return users.contains(uc);
	}
	
	public void blastUserList() throws IOException {
		Message<?> message = MessageFactory.getInstance(this.getNames());
		this.sendMessage(message);
	}
	
	public void sendMessage(Message<?> message) throws IOException {
		message.setDestination(this.id);
		for (UserConnection uc : users) {
			uc.sendMessage(message);
		}
	}
}