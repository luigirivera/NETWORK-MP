package message.format;



import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import message.FileMessage;
import message.TextMessage;

public class HTMLMessageFormatter extends MessageFormatter {
	public static String SERVER_COLOR = "#FF0000";
	public static List<String> imageExtensions = Arrays.asList("jpg","png","gif");

	@Override
	public String formatText(TextMessage message) {
		if(message.getSource()==null)
			return String.format("<font color=%s><b>SERVER : %s</b></font>", SERVER_COLOR, message.getContent());
		else
			return String.format("%s : %s", message.getSource(), message.getContent());
	}

	@Override
	public String formatFile(FileMessage message) {
		try {
			String[] nameSplit = message.getContent().getName().split("\\.");
			String ext = nameSplit[nameSplit.length-1];
			if (imageExtensions.contains(ext))
				//return String.format("<font color=#FF0000>%s : <img src=\"%s\"></font>", message.getSource(), message.getContent().getName());
				return String.format("<font color=#FF0000>%s : <img src=\"%s\" width=\"500\" height=\"500\"></font>", message.getSource(), "/Users/luisenricolopez/Desktop/spag.png");
		} catch (IndexOutOfBoundsException | IllegalStateException e) {}
		return String.format("<font color=#FF0000>%s : <a href=\"%s\">%s</a></font>", message.getSource(), message.getContent().getName(), message.getContent());
	}

}