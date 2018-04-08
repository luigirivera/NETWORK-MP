package client.view;

import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

import message.Message;
import message.format.HTMLMessageFormatter;
import message.format.MessageFormatter;

public class HTMLTextPane extends JTextPane {
	private MessageFormatter messageFormatter;
	
	public HTMLTextPane() {
		super();
		
		messageFormatter = new HTMLMessageFormatter();

		this.setEditable(false);
		this.setContentType("text/html");
		this.setText("");
		HTMLDocument doc = (HTMLDocument) this.getStyledDocument();
		doc.getStyleSheet().addRule("body {font-family: Helvetica; font-size: 14; }");
	}
	
	public void appendChat(Message<?> message) {
		this.appendChat(messageFormatter.format(message));
	}

	public void appendChat(String text) {
		/*
		 * chat.setText(chat.getText() + text + "<br>");
		 * chat.setCaretPosition(chat.getDocument().getLength());
		 */
		try {
			HTMLDocument doc = (HTMLDocument) this.getStyledDocument();
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), text + "<br>");
			this.setCaretPosition(doc.getLength());
		} catch (IOException e) {
		} catch (BadLocationException e) {
		}
	}

	public void clearChat() {
		try {
			HTMLDocument doc = (HTMLDocument) this.getStyledDocument();
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
		}
	}
	
}
