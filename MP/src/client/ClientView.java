package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
import javax.swing.JTabbedPane;
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
	private JTextField host;
	private JTextField port;
	private JTextField userName;
	private JTextField message;
	private JTextArea chat;
	private JButton sendMessage;
	private JButton sendFile;
	private JButton login;
	private JButton logout;
	private JButton createGroupDM;
	private JButton showChatRoom;
	private JButton hideChatRoom;
	private JPanel chatPanel;
	private JPanel configPanel;
	private JLabel usernameLabel;
	private JScrollPane chatScroll;
	private JScrollPane userListScroll;
	private JScrollPane messageScroll;
	private JLabel hostLabel;
	private JLabel portLabel;
	private DefaultListModel<String> usernameList;

	public ClientView(Client model) {
		super("MonoChrome");

		this.model = model;
		this.messageFormatter = new ConcreteMessageFormatter();

		init();
		setSize(765, 600);
		setLayout(null);
		setVisible(true);
		setResizable(false);
	}

	// ------------INITIALIZER------------//
	public void init() {		
		configPanel = new JPanel();
		chatPanel = new JPanel();
		
		
		this.confiPanelInit();
		this.chatPanelInit();
		
		configPanel.setBounds(0, 0, 765, 80);
		chatPanel.setBounds(0, 80, 765, 500);
		add(configPanel);
		add(chatPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void confiPanelInit() {
		userName = new JTextField();
		host = new JTextField("localhost");
		port = new JTextField("5000");
		usernameLabel = new JLabel("Username:");
		hostLabel = new JLabel("Host:");
		portLabel = new JLabel("Port:");
		login = new JButton("Login");
		logout = new JButton("Logout");
		
		userName.setPreferredSize(new Dimension(100, 25));
		host.setPreferredSize(new Dimension(100, 25));
		port.setPreferredSize(new Dimension(100, 25));
		
		logout.setVisible(false);
		
		configPanel.setLayout(null);	
		configPanel.add(hostLabel);
		configPanel.add(host);
		configPanel.add(portLabel);
		configPanel.add(port);
		configPanel.add(usernameLabel);
		configPanel.add(userName);
		configPanel.add(login);
		configPanel.add(logout);
		
		hostLabel.setBounds(10,15,50,15);
		host.setBounds(45, 10, 120,25);
		portLabel.setBounds(10,50,50,15);
		port.setBounds(45, 45, 120, 25);
		usernameLabel.setBounds(200, 15, 65, 15);
		userName.setBounds(270, 10, 120, 25);
		login.setBounds(200, 40, 190, 30);
		logout.setBounds(200, 40, 190, 30);
		
	}

	private void chatPanelInit() {
		message = new JTextField();
		chat = new JTextArea("");
		sendMessage = new JButton("Send");
		sendFile = new JButton("...");
		chatScroll = new JScrollPane();
		userListScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		usernameList = new DefaultListModel<String>();
		userList = new JList<String>(usernameList);
		createGroupDM = new JButton("+ Create Group DM");
		showChatRoom = new JButton("Show Chat Rooms");
		hideChatRoom = new JButton("Hide Chat Rooms");
		
		chatScroll.setViewportView(chat);
		userListScroll.setViewportView(userList);
		messageScroll.setViewportView(message);
		
		sendMessage.setEnabled(false);
		sendFile.setEnabled(false);
		chat.setEditable(false);
		message.setEnabled(false);
		userList.setVisible(false);
		chat.setVisible(false);
		showChatRoom.setEnabled(false);
		hideChatRoom.setVisible(false);
		hideChatRoom.setEnabled(false);
		createGroupDM.setEnabled(false);

		message.setText(placeholderName);
		message.setForeground(Color.GRAY);
		
		showChatRoom.setMargin(new Insets(0,0,0,0));
		createGroupDM.setMargin(new Insets(0,0,0,0));

		chatPanel.setLayout(null);
		chatPanel.add(messageScroll);
		chatPanel.add(showChatRoom);
		chatPanel.add(hideChatRoom);
		chatPanel.add(createGroupDM);
		chatPanel.add(chatScroll);
		chatPanel.add(userListScroll);
		chatPanel.add(sendMessage);
		chatPanel.add(sendFile);
		
		chatScroll.setBounds(5,5,550,400);
		showChatRoom.setBounds(560, 5, 175, 40);
		hideChatRoom.setBounds(560, 5, 175, 40);
		userListScroll.setBounds(560, 50, 175, 300);
		createGroupDM.setBounds(560,360, 175, 40);
		messageScroll.setBounds(5, 410, 550, 40);
		sendMessage.setBounds(560, 410, 125, 40);
		sendFile.setBounds(690, 410, 45, 40);
		
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
	
	public void addShowChatRoomsListener(ActionListener e) {
		showChatRoom.addActionListener(e);
	}
	
	public void addHideChatRoomsListener(ActionListener e) {
		hideChatRoom.addActionListener(e);
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
	
	public JTextField getHost() {
		return host;
	}

	public void setHost(JTextField host) {
		this.host = host;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public JButton getSendFile() {
		return sendFile;
	}

	public void setSendFile(JButton sendFile) {
		this.sendFile = sendFile;
	}

	public JButton getCreateGroupDM() {
		return createGroupDM;
	}

	public void setCreateGroupDM(JButton createGroupDM) {
		this.createGroupDM = createGroupDM;
	}

	public JButton getShowChatRoom() {
		return showChatRoom;
	}

	public void setShowChatRoom(JButton showChatRoom) {
		this.showChatRoom = showChatRoom;
	}
	
	public JButton getHideChatRoom() {
		return hideChatRoom;
	}

	public void setHideChatRoom(JButton hideChatRoom) {
		this.hideChatRoom = hideChatRoom;
	}
}
