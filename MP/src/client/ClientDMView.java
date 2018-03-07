package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import message.Message;
import message.format.HTMLMessageFormatter;
import message.format.MessageFormatter;

public class ClientDMView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";

	private Client model;
	private String destUser;
	private MessageFormatter messageFormatter;

	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private JTextArea dmChat;
	private JScrollPane dmChatScroll;
	private JScrollPane dmMessageScroll;
	private JButton dmSendFile;

	public ClientDMView(Client model, String destUser) {
		super(String.format("%s - MonoChrome", destUser));

		this.model = model;
		this.destUser = destUser;
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
		dmChat = new JTextArea();
		dmChatScroll = new JScrollPane();
		dmMessageScroll = new JScrollPane();
		dmSendFile = new JButton("...");

		dmChatScroll.setViewportView(dmChat);
		dmMessageScroll.setViewportView(dmMessage);

		dmChat.setEditable(false);
		dmChat.setLineWrap(true);
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

	// ------------UPDATE METHODS------------//
	@Override
	public void appendChat(Message<?> message) {
		this.appendChat(messageFormatter.format(message));
	}

	@Override
	public void appendChat(String text) {
		dmChat.setText(dmChat.getText() + text + '\n');
		dmChat.setCaretPosition(dmChat.getDocument().getLength());
	}

	@Override
	public void clearChat() {
		dmChat.setText("");
	}

	// ------------GETTERS AND SETTERS------------//
	public String getDestUser() {
		return destUser;
	}

	public void setDestUser(String destUser) {
		this.destUser = destUser;
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

	public JTextArea getDmChat() {
		return dmChat;
	}

	public void setDmChat(JTextArea dmChat) {
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
