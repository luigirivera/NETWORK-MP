package client;

import java.awt.Dimension;
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
	
	public ClientView(Client model) {
		super("Datcord");
		
		this.model = model;
		
		setSize(750,600);
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
		
		userName.setPreferredSize(new Dimension(200,30));
		
		logout.setEnabled(false);
		
		configPanel.add(usernameLabel);
		configPanel.add(userName);
		configPanel.add(login);
		configPanel.add(logout);
		
		add(configPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setVisible(true);
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
}
