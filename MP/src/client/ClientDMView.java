package client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

import message.Message;
import message.format.HTMLMessageFormatter;
import message.format.MessageFormatter;
import message.utility.MessageScope;

public class ClientDMView extends JFrame implements ChatView {
	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";

	private Client model;
	private String destination;
	private MessageFormatter messageFormatter;

	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private JTextPane dmChat;
	private JScrollPane dmChatScroll;
	private JScrollPane dmMessageScroll;
	private JButton dmSendFile;

	public ClientDMView(Client model, String destination) {
		super(String.format("%s - MonoChrome", destination));

		this.model = model;
		this.destination = destination;
		this.messageFormatter = new HTMLMessageFormatter();

		init();
		
		if(model.getSystemOS().equals("Windows"))
			this.setSize(520, 545);
		else if(model.getSystemOS().equals("Mac"))
			this.setSize(500, 520);
		else
			this.setSize(520, 545);

		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
	}

	// ------------INITIALIZER------------//

	public void init() {
		dmPanel = new JPanel();
		dmMessage = new JTextField(messagePlaceholderName);
		dmSend = new JButton("Send");
		dmChat = new JTextPane();
		dmChatScroll = new JScrollPane();
		dmMessageScroll = new JScrollPane();
		dmSendFile = new JButton("...");

		dmChatScroll.setViewportView(dmChat);
		dmMessageScroll.setViewportView(dmMessage);

		dmChat.setContentType("text/html");
		dmChat.setText("");
		dmChat.setEditable(false);
		HTMLDocument doc = (HTMLDocument) dmChat.getStyledDocument();
		doc.getStyleSheet().addRule("body {font-family: Helvetica; font-size: 14; }");
		
		dmMessage.setForeground(Color.GRAY);
		
		dmPanel.setLayout(null);
		dmPanel.add(dmChatScroll);
		dmPanel.add(dmMessageScroll);
		dmPanel.add(dmSend);
		dmPanel.add(dmSendFile);
		this.add(dmPanel);
		
		dmChatScroll.setBounds(5,5,480,420);
		dmMessageScroll.setBounds(5,430,350,50);
		dmSend.setBounds(360, 430, 75, 50);
		dmSendFile.setBounds(440, 430, 50, 50);
		dmPanel.setBounds(0, 0, 500, 520);
	}

	// ------------LISTENERS------------//
	public void addDMSendMessageListener(ActionListener e) {
		dmSend.addActionListener(e);
	}

	public void addDMMessageBoxListener(KeyListener e, FocusListener f) {
		dmMessage.addKeyListener(e);
		dmMessage.addFocusListener(f);
	}

	public void addDMWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addDmSendFileListener(ActionListener e) {
		dmSendFile.addActionListener(e);
	}

	// ------------OVERRIDE METHODS------------//
	@Override
	public MessageScope getScope() {
		return MessageScope.DIRECT;
	}
	
	@Override
	public String getDestination() {
		return destination;
	}
	
	@Override
	public void appendChat(Message<?> message) {
		this.appendChat(messageFormatter.format(message));
	}

	@Override
	public void appendChat(String text) {
		/*chat.setText(chat.getText() + text + "<br>");
		chat.setCaretPosition(chat.getDocument().getLength());*/
		try {
			HTMLDocument doc = (HTMLDocument) dmChat.getStyledDocument();
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), text + "<br>");
			dmChat.setCaretPosition(doc.getLength());
		} catch (IOException e) {
		} catch (BadLocationException e) {}
	}

	@Override
	public void clearChat() {
		try {
			HTMLDocument doc = (HTMLDocument) dmChat.getStyledDocument();
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {}
	}

	// ------------GETTERS AND SETTERS------------//

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public JPanel getDmPanel() {
		return dmPanel;
	}

	public void setDmPanel(JPanel dmPanel) {
		this.dmPanel = dmPanel;
	}

	public JTextField getDmMessage() {
		return dmMessage;
	}

	public void setDmMessage(JTextField dmMessage) {
		this.dmMessage = dmMessage;
	}

	public JButton getDmSend() {
		return dmSend;
	}

	public void setDmSend(JButton dmSend) {
		this.dmSend = dmSend;
	}

	public JTextPane getDmChat() {
		return dmChat;
	}

	public void setDmChat(JTextPane dmChat) {
		this.dmChat = dmChat;
	}

	public JScrollPane getDmChatScroll() {
		return dmChatScroll;
	}

	public void setDmChatScroll(JScrollPane dmChatScroll) {
		this.dmChatScroll = dmChatScroll;
	}

	public JScrollPane getDmMessageScroll() {
		return dmMessageScroll;
	}

	public void setDmMessageScroll(JScrollPane dmMessageScroll) {
		this.dmMessageScroll = dmMessageScroll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getMessagePlaceholdername() {
		return messagePlaceholderName;
	}

}
