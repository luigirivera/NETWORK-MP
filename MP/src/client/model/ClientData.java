package client.model;

import message.Message;

public interface ClientData {
	/*public List<Message<?>> getGlobal();
	public List<Message<?>> getDirect(String destination);
	public List<Message<?>> getGroup(String destination);*/
	public Message<?> getLast();
	public void add(Message<?> message);
	public void clear();
}
