package client;

import java.util.ArrayList;

import message.Message;
import message.utility.MessageScope;

public class ChatViewList extends ArrayList<ChatView>{
	private static final long serialVersionUID = 1L;
	
	public ChatView getAssociatedView(Message<?> message) {
		switch (message.getScope()) {
			case GLOBAL:
				return this.getGlobal();
			case DIRECT:
				return this.getDirect(message);
			case GROUP:
				return this.getGroup(message);
			case ROOM:
				return this.getRoom(message);
			default: return null;
		}
	}
	
	public ChatView getGlobal() {
		for(ChatView cv : this) {
			if(cv.getScope()==MessageScope.GLOBAL) {
				return cv;
			}
		}
		return null;
	}
	
	public ChatView getDirect(Message<?> message) {
		for(ChatView cv : this) {
			if(cv.getScope()==MessageScope.DIRECT && (cv.getDestination().equalsIgnoreCase(message.getSource())||cv.getDestination().equalsIgnoreCase(message.getDestination()))) {
				return cv;
			}
		}
		return null;
	}
	
	public ChatView getGroup(Message<?> message) {
		for(ChatView cv : this) {
			if(cv.getScope()==MessageScope.GROUP && (cv.getDestination().equalsIgnoreCase(message.getSource())||cv.getDestination().equalsIgnoreCase(message.getDestination()))) {
				return cv;
			}
		}
		return null;
	}
	
	public ChatView getRoom(Message<?> message) {
		for(ChatView cv : this) {
			if(cv.getScope()==MessageScope.ROOM && (cv.getDestination().equalsIgnoreCase(message.getSource())||cv.getDestination().equalsIgnoreCase(message.getDestination()))) {
				return cv;
			}
		}
		return null;
	}
}
