package message.format;

import message.FileMessage;
import message.TextMessage;

public class PlainMessageFormatter extends MessageFormatter {
	@Override
	public String formatText(TextMessage message) {
		return String.format("%s : %s", message.getSource(), message.getContent());
	}
	
	@Override
	public String formatFile(FileMessage message) {
		return String.format("%s : %s", message.getSource(), message.getContent());
	}
}
