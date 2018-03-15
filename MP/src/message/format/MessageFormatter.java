package message.format;

import message.FileMessage;
import message.Message;
import message.TextMessage;

public abstract class MessageFormatter {
	public String format(Message<?> message) {
		if (message instanceof TextMessage)
			return this.formatText((TextMessage) message);
		else if (message instanceof FileMessage)
			return this.formatFile((FileMessage) message);
		else
			return "Could not format message of type: " + message.getClass().getSimpleName();
	}
	
	public abstract String formatText(TextMessage message);
	public abstract String formatFile(FileMessage message);
}