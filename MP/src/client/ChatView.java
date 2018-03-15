package client;

import message.Message;
import message.content.UsernameList;
import message.utility.MessageScope;

public interface ChatView {
	public MessageScope getScope();
	public String getDestination();
	public void appendChat(Message<?> message);
	public void appendChat(String text);
	public void clearChat();
}

interface ShowsUsernameList {
	public void updateUsernameList(UsernameList usernames);
}