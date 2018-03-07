package message.format;

import message.Message;

public class PlainMessageFormatter implements MessageFormatter {
	@Override
	public String format(Message<?> message) {
		return String.format("%s : %s", message.getSource(), message.getContent());
	}
}
