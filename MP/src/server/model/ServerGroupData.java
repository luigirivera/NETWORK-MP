package server.model;

import java.util.List;

public interface ServerGroupData {
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
