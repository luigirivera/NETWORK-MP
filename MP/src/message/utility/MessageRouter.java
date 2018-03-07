package message.utility;

import message.Message;

public interface MessageRouter {
	public void route(Message<?> message);
}
