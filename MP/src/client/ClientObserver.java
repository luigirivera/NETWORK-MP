package client;

import shared.Message;

public interface ClientObserver {
	public void appendChat(Message message);
	public void clearChat();
}
