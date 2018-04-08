package server.model;

import java.io.IOException;

import message.Message;
import message.content.ChatRoomInfo;
import message.utility.MessageFactory;
import message.utility.MessageScope;

public class PublicChatGroup extends ChatGroup {
	private String name;
	private String password;
	
	public PublicChatGroup(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public void add(UserConnection uc) {
		if (!users.contains(uc)) {
			users.add(uc);
			try {
				this.sendInfo();
				this.blastUserList();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendInfo() throws IOException {
		Message<?> message = MessageFactory.getInstance(new ChatRoomInfo(this.id, this.name));
		this.sendMessage(message);
	}
	
	@Override
	public void sendMessage(Message<?> message) throws IOException {
		message.setScope(MessageScope.ROOM);
		super.sendMessage(message);
	}
}