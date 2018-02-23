package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ClientView extends JFrame {
	private static final long serialVersionUID = 1L;
	private Client model;
	
	private JList<String> userList;
	private JTextField userName;
	private JTextField message;
	private JTextArea chat;
	private JButton sendMessage;
	private JButton login;
	private JButton logout;
	private JPanel chatPanel;
	private JPanel configPanel;
	private JLabel usernameLabel;
	private JScrollPane chatScroll;
	private JScrollPane userListScroll;
	private JScrollPane messageScroll;
	
	private JFrame dmFrame;
	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private JTextArea dmChat;
	private JScrollPane dmChatScroll;
	
	public ClientView(Client model) {
		super("MonoChrome");
		
		this.model = model;
		
		init();
		setSize(765,600);
		setLayout(null);
		setVisible(true);
		setResizable(false);
	}
	
	public void init() {
		userList = new JList<String>(); //add the usernames to the JList as a parameter
		userName = new JTextField();
		message = new JTextField();
		chat = new JTextArea();
		sendMessage = new JButton("Send");
		login = new JButton("Login");
		logout = new JButton("Logout");
		chatPanel = new JPanel();
		configPanel = new JPanel();
		usernameLabel = new JLabel("Username:");
		chatScroll = new JScrollPane();
		userListScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		dmFrame = new JFrame("<name> - MonoChrome");
		dmPanel = new JPanel();
		dmMessage = new JTextField();
		dmSend = new JButton();
		dmChat = new JTextArea();
		dmChatScroll = new JScrollPane();
		
		userName.setPreferredSize(new Dimension(200,30));
		
		chatScroll.setViewportView(chat);
		userListScroll.setViewportView(userList);
		messageScroll.setViewportView(message);
		dmChatScroll.setViewportView(dmChat);
		
		logout.setEnabled(false);
		sendMessage.setEnabled(false);
		chat.setEditable(false);
		message.setEnabled(false);
		
		chatScroll.setPreferredSize(new Dimension(600,400));
		userListScroll.setPreferredSize(new Dimension(100,400));
		messageScroll.setPreferredSize(new Dimension(600,40));
		
		configPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		chatPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		configPanel.add(usernameLabel);
		configPanel.add(userName);
		configPanel.add(login);
		configPanel.add(logout);
		
		chatPanel.add(chatScroll);
		chatPanel.add(userListScroll);
		chatPanel.add(messageScroll);
		chatPanel.add(sendMessage);
		
		configPanel.setBounds(0, 0, 750, 50);
		chatPanel.setBounds(0, 50, 750, 511);
		
		add(configPanel);
		add(chatPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addLoginListener(ActionListener e) {
		login.addActionListener(e);
	}
	
	public void addLogoutListener(ActionListener e) {
		logout.addActionListener(e);
	}
	
	public void addSendMessageListener(ActionListener e) {
		sendMessage.addActionListener(e);
	}
	
	public Client getModel() {
		return model;
	}

	public void setModel(Client model) {
		this.model = model;
	}

	public JList getUserList() {
		return userList;
	}

	public void setUserList(JList userList) {
		this.userList = userList;
	}

	public JTextField getUserName() {
		return userName;
	}

	public void setUserName(JTextField userName) {
		this.userName = userName;
	}

	public JTextField getMessage() {
		return message;
	}

	public void setMessage(JTextField message) {
		this.message = message;
	}

	public JTextArea getChat() {
		return chat;
	}

	public void setChat(JTextArea chat) {
		this.chat = chat;
	}

	public JButton getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(JButton sendMessage) {
		this.sendMessage = sendMessage;
	}

	public JButton getLogin() {
		return login;
	}

	public void setLogin(JButton login) {
		this.login = login;
	}

	public JButton getLogout() {
		return logout;
	}

	public void setLogout(JButton logout) {
		this.logout = logout;
	}

	public JPanel getChatPanel() {
		return chatPanel;
	}

	public void setChatPanel(JPanel chatPanel) {
		this.chatPanel = chatPanel;
	}

	public JPanel getConfigPanel() {
		return configPanel;
	}

	public void setConfigPanel(JPanel configPanel) {
		this.configPanel = configPanel;
	}

	public JLabel getUsernameLabel() {
		return usernameLabel;
	}

	public void setUsernameLabel(JLabel usernameLabel) {
		this.usernameLabel = usernameLabel;
	}
	
	public JScrollPane getChatScroll() {
		return chatScroll;
	}

	public void setChatScroll(JScrollPane chatScroll) {
		this.chatScroll = chatScroll;
	}

	public JScrollPane getUserListScroll() {
		return userListScroll;
	}

	public void setUserListScroll(JScrollPane userListScroll) {
		this.userListScroll = userListScroll;
	}

	public JScrollPane getMessageScroll() {
		return messageScroll;
	}

	public void setMessageScroll(JScrollPane messageScroll) {
		this.messageScroll = messageScroll;
	}
}
