package client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.ClientGroupDMController.MessageBoxFocusListener;
import client.ClientGroupDMController.MessageBoxKeyListener;
import shared.Message;

public class ClientGroupDMView extends JFrame implements ClientObserver {

	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";

	private JTextArea chat;
	private JButton invite;
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
	
	public ClientGroupDMView(Client model, String name) {
		super(String.format("%s - MonoChrome", name));
		
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
		panel = new JPanel();
		chat = new JTextArea();
		invite = new JButton("+ Invite");
		sendMessage = new JButton("Send");
		sendFile = new JButton("...");
		membersList = new DefaultListModel<String>();
		members = new JList<String>(membersList);
		message = new JTextField(messagePlaceholderName);
		membersScroll = new JScrollPane();
		chatScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		leave = new JButton("Leave Group DM");
		
		membersScroll.setViewportView(members);
		chatScroll.setViewportView(chat);
		messageScroll.setViewportView(message);
		
		chat.setLineWrap(true);
		chat.setEditable(false);
		message.setForeground(Color.GRAY);
		
		panel.setLayout(null);
		panel.add(chatScroll);
		panel.add(membersScroll);
		panel.add(messageScroll);
		panel.add(sendMessage);
		panel.add(sendFile);
		panel.add(invite);
		panel.add(leave);
		
		add(panel);
		panel.setBounds(0,0,750,500);
		chatScroll.setBounds(5,5,550,400);
		messageScroll.setBounds(5, 425, 550, 50);
		sendMessage.setBounds(560, 425, 120,50);
		sendFile.setBounds(690, 425, 50, 50);
		invite.setBounds(560, 5, 175, 40);
		membersScroll.setBounds(560, 50, 175, 300);
		leave.setBounds(560, 360, 175, 50);
	}
	
	// ------------LISTENERS------------//
	
	public void addInviteListener(ActionListener e) {
		invite.addActionListener(e);
	}
	
	public void addMessageBoxListener(KeyListener e,FocusListener f) {
		message.addKeyListener(e);
		message.addFocusListener(f);
	}
	
	public void addSendMessageListener(ActionListener e) {
		sendMessage.addActionListener(e);
	}
	
	public void addSendFileListener(ActionListener e) {
		sendFile.addActionListener(e);
	}
	
	// ------------UPDATE METHODS------------//

	@Override
	public void appendChat(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearChat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appendChat(Message message) {
		// TODO Auto-generated method stub
		
	}
	
	// ------------GETTERS AND SETTERS------------//

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

	public JTextField getMessage() {
		return message;
	}

	public void setMessage(JTextField message) {
		this.message = message;
	}
	
	public static String getMessageplaceholdername() {
		return messagePlaceholderName;
	}
	
	public JScrollPane getMembersScroll() {
		return membersScroll;
	}

	public void setMembersScroll(JScrollPane membersScroll) {
		this.membersScroll = membersScroll;
	}

}
