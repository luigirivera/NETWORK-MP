package client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

import message.Message;
import message.content.UsernameList;
import message.format.MessageFormatter;
import message.utility.MessageScope;

public class ClientChatRoomView extends JFrame implements ChatView, ShowsUsernameList{
	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";
	
	private Client model;
	private String destination;
	private MessageFormatter messageFormatter;

	private JTextPane chat;
	private JLabel password;
	private JButton sendMessage;
	private JButton sendFile;
	private JButton leave;
	private JTextField message;
	private JList<String> members;
	private JScrollPane membersScroll;
	private JPopupMenu dmMenu;
	private JMenuItem privateDM;
	private JMenuItem createGroupDM;
	private JScrollPane chatScroll;
	private JScrollPane messageScroll;
	private JPanel panel;
	private DefaultListModel<String> membersList;
	
	public ClientChatRoomView(Client model, String name, String destination) {
		super(String.format("%s Chat Room", name));
		
		this.model = model;
		this.destination = destination;
		
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
		chat = new JTextPane();
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
		dmMenu = new JPopupMenu();
		privateDM = new JMenuItem("Message");
		createGroupDM = new JMenuItem("Group Message");
		
		membersScroll.setViewportView(members);
		chatScroll.setViewportView(chat);
		messageScroll.setViewportView(message);
		
		chat.setContentType("text/html");
		chat.setText("");
		chat.setEditable(false);
		HTMLDocument doc = (HTMLDocument) chat.getStyledDocument();
		doc.getStyleSheet().addRule("body {font-family: Helvetica; font-size: 14; }");
		
		message.setForeground(Color.GRAY);
		
		dmMenu.add(privateDM);
		dmMenu.add(createGroupDM);
		
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
	
	public void addMessageMenuListener(ActionListener e) {
		privateDM.addActionListener(e);
	}
	
	public void addGroupMessageMenuListener(ActionListener e) {
		createGroupDM.addActionListener(e);
		
	}
	// ------------OVERRIDE METHODS------------//

	@Override
	public MessageScope getScope() {
		return MessageScope.ROOM;
	}

	@Override
	public String getDestination() {
		return this.destination;
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
			HTMLDocument doc = (HTMLDocument) chat.getStyledDocument();
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), text + "<br>");
			chat.setCaretPosition(doc.getLength());
		} catch (IOException e) {
		} catch (BadLocationException e) {}
	}

	@Override
	public void clearChat() {
		try {
			HTMLDocument doc = (HTMLDocument) chat.getStyledDocument();
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {}
	}
	
	@Override
	public void updateUsernameList(UsernameList usernames) {
		membersList.clear();
		for (String str : usernames) {
			membersList.addElement(str);
		}
	}
	
	// ------------GETTERS AND SETTERS------------//

	public JTextPane getChat() {
		return chat;
	}

	public void setChat(JTextPane chat) {
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
	
	public JList<String> getMembers() {
		return members;
	}

	public void setMembers(JList<String> members) {
		this.members = members;
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
