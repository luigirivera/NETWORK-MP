package client.model;

import message.Message;

public class DefaultClientData implements ClientData {
	//private List<Message<?>> messages;
	private Message<?> lastMessage;

	public DefaultClientData() {
		//messages = new ArrayList<Message<?>>();
	}

	/*@Override
	public List<Message<?>> getGlobal() {
		List<Message<?>> tmp = new ArrayList<Message<?>>();
		for (Message<?> message : messages) {
			if (message.getScope().equals(MessageScope.GLOBAL)) {
				tmp.add(message);
			}
		}
		return tmp;
	}

	@Override
	public List<Message<?>> getDirect(String destination) {
		List<Message<?>> tmp = new ArrayList<Message<?>>();
		for (Message<?> message : messages) {
			if (message.getScope().equals(MessageScope.DIRECT) && message.getDestination().equalsIgnoreCase(destination)) {
				tmp.add(message);
			}
		}
		return tmp;
	}

	@Override
	public List<Message<?>> getGroup(String destination) {
		List<Message<?>> tmp = new ArrayList<Message<?>>();
		for (Message<?> message : messages) {
			if (message.getScope().equals(MessageScope.GROUP) && message.getDestination().equalsIgnoreCase(destination)) {
				tmp.add(message);
			}
		}
		return tmp;
	}*/
	
	@Override
	public Message<?> getLast() {
		return this.lastMessage;
	}

	@Override
	public void add(Message<?> message) {
		//messages.add(message);
		this.lastMessage = message;
	}

	@Override
	public void clear() {
		//messages.clear();
		this.lastMessage = null;
	}

}
