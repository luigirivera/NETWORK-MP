package message.format;

import message.FileMessage;
import message.Message;
import message.TextMessage;

public class HTMLMessageFormatter extends MessageFormatter {

	@Override
	public String formatText(TextMessage message) {
		return String.format("<font color=#FF0000>%s : %s</font>", message.getSource(), message.getContent());
	}

	@Override
	public String formatFile(FileMessage message) {
		return String.format("<font color=#FF0000>%s : <a href=\"%s\">%s</a></font>", message.getSource(), message.getContent().getName(), message.getContent());
	}

}