package client.model;

import client.view.ClientObserver;
import message.Message;

public interface ClientModel {
	public void updateAll();
	public void attach(ClientObserver obs);
	public void detach(ClientObserver obs);
	public boolean hasDMObserver(String dest);
	public boolean hasGroupObserver(String dest);
	public boolean hasRoomObserver(String dest);
	public String getUsername();
	public void setUsername(String username);
	/*public List<Message<?>> getGlobal();
	public List<Message<?>> getDirect(String destination);
	public List<Message<?>> getGroup(String destination);*/
	public Message<?> getLast();
	public void add(Message<?> message);
	public void clear();
}
