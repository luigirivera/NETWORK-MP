package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

public class ClientView extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String DM_TITLE_TEXT = " - MonoChrome";
	private String placeholderName = "Message";
	
	private Client model;
	
	private JList userList;
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
	private DefaultListModel usernameList;
	
	private JFrame dmFrame;
	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private JTextArea dmChat;
	private JScrollPane dmChatScroll;
	private JScrollPane dmMessageScroll;
	
	public ClientView(Client model) {
		super("MonoChrome");
		
		this.model = model;
		
		init();
		setSize(750,583);
		setLayout(null);
		setVisible(true);
		setResizable(false);
	}

//------------INITIALIZERS------------
	public void init() {
		this.mainChatInit();
		this.dmChatInit();
	}
	
	private void mainChatInit() {
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
		usernameList = new DefaultListModel();
		userList = new JList(usernameList);
		
		userName.setPreferredSize(new Dimension(200,30));
		chatScroll.setPreferredSize(new Dimension(600,400));
		userListScroll.setPreferredSize(new Dimension(100,400));
		messageScroll.setPreferredSize(new Dimension(600,40));
		
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

	private void dmChatInit() {
		dmFrame = new JFrame("<name> - MonoChrome"); //replace <name> with getting the name chosen
		dmPanel = new JPanel();
		dmMessage = new JTextField();
		dmSend = new JButton("Send");
		dmChat = new JTextArea();
		dmChatScroll = new JScrollPane();
		dmMessageScroll = new JScrollPane();
		
		dmChatScroll.setViewportView(dmChat);
		dmMessageScroll.setViewportView(dmMessage);
		
		dmChatScroll.setPreferredSize(new Dimension(480,420));
		dmMessageScroll.setPreferredSize(new Dimension(400,40));
		
		dmChat.setEditable(false);
		
		dmPanel.add(dmChatScroll);
		dmPanel.add(dmMessageScroll);
		dmPanel.add(dmSend);
		dmFrame.add(dmPanel);
		
		dmPanel.setBounds(0,0,500,478);
		dmFrame.setSize(500,500);
		dmFrame.setLayout(null);
		dmFrame.setResizable(false);
	}

//------------LISTENERS------------//
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
	
	public void addDMSendMessageListener(ActionListener e) {
		dmSend.addActionListener(e);
	}
	
	public void addDMMessageBoxListener(KeyListener e, FocusListener f) {
		dmMessage.addKeyListener(e);
		dmMessage.addFocusListener(f);
	}
	
	public void addUserListListener(MouseListener e) {
		userList.addMouseListener(e);
	}
	
//------------UPDATE METHODS------------//
	public void appendChat(String text) {
		chat.setText(chat.getText()+'\n'+text);
	}
	
	public void clearChat() {
		chat.setText("");
	}

//------------GETTERS AND SETTERS------------//
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

	public JFrame getDmFrame() {
		return dmFrame;
	}

	public void setDmFrame(JFrame dmFrame) {
		this.dmFrame = dmFrame;
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
	
	public DefaultListModel getUsernameList() {
		return usernameList;
	}

	public void setUsernameList(DefaultListModel usernameList) {
		this.usernameList = usernameList;
	}

	public JPanel getDmPanel() {
		return dmPanel;
	}

	public void setDmPanel(JPanel dmPanel) {
		this.dmPanel = dmPanel;
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

	public static String getDmTitleText() {
		return DM_TITLE_TEXT;
	}
}
