package server.model;

import java.util.ArrayList;
import java.util.List;

public class DefaultServerGroupData implements ServerGroupData {
	private List<ChatGroup> groups;

	public DefaultServerGroupData() {
		this.groups = new ArrayList<ChatGroup>();
	}

	@Override
	public List<ChatGroup> getAll() {
		return groups;
	}

	@Override
	public List<PrivateChatGroup> getPrivate() {
		List<PrivateChatGroup> list = new ArrayList<PrivateChatGroup>();
		for (ChatGroup group : groups) {
			if (group instanceof PrivateChatGroup)
				list.add((PrivateChatGroup) group);
		}
		return list;
	}

	@Override
	public List<PublicChatGroup> getPublic() {
		List<PublicChatGroup> list = new ArrayList<PublicChatGroup>();
		for (ChatGroup group : groups) {
			if (group instanceof PublicChatGroup)
				list.add((PublicChatGroup) group);
		}
		return list;
	}

	@Override
	public ChatGroup get(String id) {
		for (ChatGroup group : groups) {
			if (group.getId().equals(id))
				return group;
		}
		return null;
	}

	@Override
	public void addUserToGroup(UserConnection user, ChatGroup group) {
		for (ChatGroup group2 : groups) {
			if (group.equals(group2)) {
				group2.add(user);
				break;
			}
		}
	}

	@Override
	public void removeUserFromGroup(UserConnection user, ChatGroup group) {
		for (ChatGroup group2 : groups) {
			if (group.equals(group2)) {
				group2.remove(user);
				if (group2.isEmpty())
					this.remove(group2);
				break;
			}
		}
	}

	@Override
	public void removeUserFromAll(UserConnection user) {
		List<ChatGroup> empty = new ArrayList<ChatGroup>();
		for (ChatGroup group2 : groups) {
			group2.remove(user);
			if (group2.isEmpty())
				empty.add(group2);
		}
		for (ChatGroup group3 : empty) {
			groups.remove(group3);
		}
	}

	@Override
	public void add(ChatGroup group) {
		groups.add(group);
	}

	@Override
	public void remove(ChatGroup group) {
		groups.remove(group);
	}

}
