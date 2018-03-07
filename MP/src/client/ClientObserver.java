package client;

import message.Message;

public interface ClientObserver {
	public void appendChat(Message<?> message);
	public void appendChat(String text);
	public void clearChat();
}
