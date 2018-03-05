package client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.ClientChatRoomController.MessageBoxFocusListener;
import client.ClientChatRoomController.MessageBoxKeyListener;

public class ClientChatRoomView extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";

	private JTextArea chat;
	private JLabel password;
	private JButton sendMessage;
	private JButton sendFile;
	private JButton leave;
	private JTextField message;
	private JList<String> members;
	private JScrollPane membersScroll;
	private JScrollPane chatScroll;
	private JScrollPane messageScroll;
	private JPanel panel;
	private DefaultListModel<String> membersList;
	private Client model;
	
	public ClientChatRoomView(Client model, String name) {
		super(String.format("%s Chat Room", name));
		
		this.model = model;
		
		if(model.getSystemOS().equals("Windows"))
			this.setSize(770, 525);
		else if(model.getSystemOS().equals("Mac"))
			this.setSize(750, 500);
		else
			this.setSize(770, 525);
		
		
		this.init();
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	// ------------INITIALIZER------------//
	
	private void init() {
		String passwordText = "<html><div style='text-align: center;'>Password:<br/>(password here)</div></html>";
		panel = new JPanel();
		chat = new JTextArea();
		password = new JLabel(passwordText, SwingConstants.CENTER);
		sendMessage = new JButton("Send");
		sendFile = new JButton("...");
		membersList = new DefaultListModel<String>();
		members = new JList<String>(membersList);
		message = new JTextField(messagePlaceholderName);
		membersScroll = new JScrollPane();
		chatScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		leave = new JButton("Leave Chat Room");
		
		membersScroll.setViewportView(members);
		chatScroll.setViewportView(chat);
		messageScroll.setViewportView(message);
		
		chat.setEditable(false);
		chat.setLineWrap(true);
		message.setForeground(Color.GRAY);
		
		panel.setLayout(null);
		panel.add(chatScroll);
		panel.add(membersScroll);
		panel.add(messageScroll);
		panel.add(sendMessage);
		panel.add(sendFile);
		panel.add(password);
		panel.add(leave);
		
		add(panel);
		panel.setBounds(0,0,750,500);
		chatScroll.setBounds(5,5,550,400);
		messageScroll.setBounds(5, 425, 550, 50);
		sendMessage.setBounds(560, 425, 120,50);
		sendFile.setBounds(690, 425, 50, 50);
		password.setBounds(560, 5, 175, 40);
		membersScroll.setBounds(560, 50, 175, 300);
		leave.setBounds(560, 360, 175, 40);
	}


	
	// ------------LISTENERS------------//
	public void addSendFileListener(ActionListener e) {
		sendFile.addActionListener(e);
	}
	
	public void addMessageBoxListener(KeyListener e, FocusListener f) {
		message.addKeyListener(e);
		message.addFocusListener(f);		
	}
	
	public void addSendMessageListener(ActionListener e) {
		sendMessage.addActionListener(e);
	}
	// ------------UPDATE METHODS------------//
	// ------------GETTERS AND SETTERS------------//
	public JTextArea getChat() {
		return chat;
	}

	public void setChat(JTextArea chat) {
		this.chat = chat;
	}

	public JLabel getPassword() {
		return password;
	}

	public void setPassword(JLabel password) {
		this.password = password;
	}

	public JButton getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(JButton sendMessage) {
		this.sendMessage = sendMessage;
	}

	public JButton getSendFile() {
		return sendFile;
	}

	public void setSendFile(JButton sendFile) {
		this.sendFile = sendFile;
	}

	public JButton getLeave() {
		return leave;
	}

	public void setLeave(JButton leave) {
		this.leave = leave;
	}

	public JTextField getMessage() {
		return message;
	}

	public void setMessage(JTextField message) {
		this.message = message;
	}
	
	public static String getMessageplaceholdername() {
		return messagePlaceholderName;
	}

	public JScrollPane getMessageScroll() {
		return messageScroll;
	}

	public void setMessageScroll(JScrollPane messageScroll) {
		this.messageScroll = messageScroll;
	}
}
