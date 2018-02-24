package client;

public interface ClientObserver {
	public void appendChat(String text);
	public void clearChat();
}
