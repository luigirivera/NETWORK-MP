package client;

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

public class ClientChatRoomView extends JFrame {
	private static final long serialVersionUID = 1L;
	
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
	public ClientChatRoomView() {
		super("<name> Chat Room");
		
		this.setSize(750, 500);
		
		this.init();
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private void init() {
		String passwordText = "<html><div style='text-align: center;'>Password:<br/>(password here)</div></html>";
		panel = new JPanel();
		chat = new JTextArea();
		password = new JLabel(passwordText, SwingConstants.CENTER);
		sendMessage = new JButton("Send");
		sendFile = new JButton("...");
		membersList = new DefaultListModel<String>();
		members = new JList<String>(membersList);
		message = new JTextField("Message");
		membersScroll = new JScrollPane();
		chatScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		leave = new JButton("Leave Chat Room");
		
		membersScroll.setViewportView(members);
		chatScroll.setViewportView(chat);
		messageScroll.setViewportView(message);
		
		chat.setEditable(false);
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
		messageScroll.setBounds(5, 425, 550, 40);
		sendMessage.setBounds(560, 425, 120,40);
		sendFile.setBounds(690, 425, 50, 40);
		password.setBounds(560, 5, 175, 40);
		membersScroll.setBounds(560, 50, 175, 300);
		leave.setBounds(560, 360, 175, 40);
	}
}
