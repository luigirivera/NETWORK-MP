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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import shared.ConcreteMessageFormatter;
import shared.Message;
import shared.MessageFormatter;

public class ClientGlobalView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private String placeholderName = "Message";

	private Client model;
	private MessageFormatter messageFormatter;

	private JList<String> userList;
	private JTextField message;
	private JTextArea chat;
	private JButton sendMessage;
	private JButton sendFile;
	private JButton logout;
	private JButton showChatRoom;
	private JButton hideChatRoom;
	private JPanel chatPanel;
	private JPanel logoPanel;
	private JPanel configPanel;
	private JScrollPane chatScroll;
	private JScrollPane userListScroll;
	private JScrollPane messageScroll;
	private JPopupMenu dmMenu;
	private JMenuItem privateDM;
	private JMenuItem createGroupDM;
	private DefaultListModel<String> usernameList;

	public ClientGlobalView(Client model) {
		super("MonoChrome");

		this.model = model;
		this.messageFormatter = new ConcreteMessageFormatter();

		init();
		setSize(765, 600);
		setLayout(null);
		setVisible(false);
		setResizable(false);
	}

	// ------------INITIALIZER------------//
	public void init() {		
		logoPanel = new JPanel();
		chatPanel = new JPanel();
		
		this.chatPanelInit();
		
		logoPanel.setBounds(0, 0, 765, 80);
		chatPanel.setBounds(0, 80, 765, 500);
		add(logoPanel);
		add(chatPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		logout = new JButton("Logout");
		showChatRoom = new JButton("Show Chat Rooms");
		hideChatRoom = new JButton("Hide Chat Rooms");
		dmMenu = new JPopupMenu();
		privateDM = new JMenuItem("Message");
		createGroupDM = new JMenuItem("Group Message");
		
		chatScroll.setViewportView(chat);
		userListScroll.setViewportView(userList);
		messageScroll.setViewportView(message);

		message.setText(placeholderName);
		message.setForeground(Color.GRAY);
		
		dmMenu.add(privateDM);
		dmMenu.add(createGroupDM);
		
		showChatRoom.setMargin(new Insets(0,0,0,0));
		logout.setMargin(new Insets(0,0,0,0));

		chatPanel.setLayout(null);
		chatPanel.add(messageScroll);
		chatPanel.add(showChatRoom);
		chatPanel.add(hideChatRoom);
		chatPanel.add(logout);
		chatPanel.add(chatScroll);
		chatPanel.add(userListScroll);
		chatPanel.add(sendMessage);
		chatPanel.add(sendFile);
		
		chatScroll.setBounds(5,5,550,400);
		showChatRoom.setBounds(560, 5, 175, 40);
		hideChatRoom.setBounds(560, 5, 175, 40);
		userListScroll.setBounds(560, 50, 175, 300);
		logout.setBounds(560,360, 175, 40);
		messageScroll.setBounds(5, 410, 550, 40);
		sendMessage.setBounds(560, 410, 125, 40);
		sendFile.setBounds(690, 410, 45, 40);
		
	}


	// ------------LISTENERS------------//

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
	
	public void addMessageMenuListener(ActionListener e) {
		privateDM.addActionListener(e);
	}
	
	public void addDMMessageMenuListener(ActionListener e) {
		createGroupDM.addActionListener(e);
		
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

	public JButton getSendFile() {
		return sendFile;
	}

	public void setSendFile(JButton sendFile) {
		this.sendFile = sendFile;
	}

	public JButton getLogout() {
		return logout;
	}

	public void setLogout(JButton logout) {
		this.logout = logout;
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
	
	public JPopupMenu getDmMenu() {
		return dmMenu;
	}

	public void setDmMenu(JPopupMenu dmMenu) {
		this.dmMenu = dmMenu;
	}

	public JMenuItem getPrivateDM() {
		return privateDM;
	}

	public void setPrivateDM(JMenuItem privateDM) {
		this.privateDM = privateDM;
	}

	public JMenuItem getCreateGroupDM() {
		return createGroupDM;
	}

	public void setCreateGroupDM(JMenuItem createGroupDM) {
		this.createGroupDM = createGroupDM;
	}
}
