package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import shared.ConcreteMessageFormatter;
import shared.Message;
import shared.MessageFormatter;

public class ClientView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private String placeholderName = "Message";

	private Client model;
	private MessageFormatter messageFormatter;

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
	private DefaultListModel<String> usernameList;

	public ClientView(Client model) {
		super("MonoChrome");

		this.model = model;
		this.messageFormatter = new ConcreteMessageFormatter();

		init();
		setSize(750, 583);
		setLayout(null);
		setVisible(true);
		setResizable(false);
	}

	// ------------INITIALIZER------------//
	public void init() {
		userName = new JTextField();
		message = new JTextField();
		chat = new JTextArea("");
		sendMessage = new JButton("Send");
		login = new JButton("Login");
		logout = new JButton("Logout");
		chatPanel = new JPanel();
		configPanel = new JPanel();
		usernameLabel = new JLabel("Username:");
		chatScroll = new JScrollPane();
		userListScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		usernameList = new DefaultListModel<String>();
		userList = new JList<String>(usernameList);

		userName.setPreferredSize(new Dimension(200, 30));
		chatScroll.setPreferredSize(new Dimension(600, 400));
		userListScroll.setPreferredSize(new Dimension(100, 400));
		messageScroll.setPreferredSize(new Dimension(600, 40));

		chatScroll.setViewportView(chat);
		userListScroll.setViewportView(userList);
		messageScroll.setViewportView(message);

		logout.setEnabled(false);
		sendMessage.setEnabled(false);
		chat.setEditable(false);
		message.setEnabled(false);
		userList.setVisible(false);
		chat.setVisible(false);

		configPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		chatPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		message.setText(placeholderName);
		message.setForeground(Color.GRAY);

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

	// ------------LISTENERS------------//
	public void addLoginBoxListener(KeyListener e) {
		userName.addKeyListener(e);
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

	public void addMessageBoxListener(KeyListener e, FocusListener f) {
		message.addKeyListener(e);
		message.addFocusListener(f);
	}

	public void addUserListListener(MouseListener e) {
		userList.addMouseListener(e);
	}

	// ------------UPDATE METHODS------------//
	@Override
	public void appendChat(Message message) {
		this.appendChat(messageFormatter.format(message));
	}
	
	@Override
	public void appendChat(String text) {
		chat.setText(chat.getText() + text + '\n');
		chat.setCaretPosition(chat.getDocument().getLength());
	}

	@Override
	public void clearChat() {
		chat.setText("");
	}

	// ------------GETTERS AND SETTERS------------//
	public Client getModel() {
		return model;
	}

	public void setModel(Client model) {
		this.model = model;
	}

	public JList<String> getUserList() {
		return userList;
	}

	public void setUserList(JList<String> userList) {
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

	public DefaultListModel<String> getUsernameList() {
		return usernameList;
	}

	public void setUsernameList(DefaultListModel<String> usernameList) {
		this.usernameList = usernameList;
	}
}
