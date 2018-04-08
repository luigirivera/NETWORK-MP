package server.model;

import java.util.List;

import server.view.ServerGroupObserver;

public interface ServerGroupModel {
	public void updateAll();
	public void attach(ServerGroupObserver obs);
	public void detach(ServerGroupObserver obs);
	public List<ChatGroup> getAll();
	public List<PrivateChatGroup> getPrivate();
	public List<PublicChatGroup> getPublic();
	public ChatGroup get(String id);
	public void addUserToGroup(UserConnection user, ChatGroup group);
	public void removeUserFromGroup(UserConnection user, ChatGroup group);
	public void removeUserFromAll(UserConnection user);
	public void add(ChatGroup group);
	public void remove(ChatGroup group);
}
