package message.format;

import message.Message;

public interface MessageFormatter {
	public String format(Message<?> message);
}