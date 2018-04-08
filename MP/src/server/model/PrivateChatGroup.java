package server.model;

import java.io.IOException;

import message.Message;
import message.utility.MessageScope;

public class PrivateChatGroup extends ChatGroup {
	@Override
	public void sendMessage(Message<?> message) throws IOException {
		message.setScope(MessageScope.GROUP);
		super.sendMessage(message);
	}
}
