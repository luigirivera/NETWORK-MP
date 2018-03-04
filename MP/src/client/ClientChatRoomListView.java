package client;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shared.Message;

public class ClientChatRoomListView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	
	private Client model;
	
	private JScrollPane chatRoomListScroll;
	private JList<String> chatRoomList;
	private DefaultListModel<String> chatRoomsList;
	private JButton addChatRoom;
	private JPanel panel;
	
	public ClientChatRoomListView(Client model) {
		super("MonoChrome - Chat Rooms");
		
		this.model = model;
		this.init();
		setSize(400, 600);
		setLayout(null);
		setResizable(false);
		setVisible(false);
	}
	
	private void init() {
		chatRoomListScroll = new JScrollPane();
		chatRoomsList = new DefaultListModel<String>();
		chatRoomList = new JList<String> (chatRoomsList);
		addChatRoom = new JButton("+ Create Chat Room");
		panel = new JPanel();
		
		chatRoomListScroll.setViewportView(chatRoomList);
		
		panel.add(chatRoomListScroll);
		panel.add(addChatRoom);
		
		addChatRoom.setMargin(new Insets(0,0,0,0));
		panel.setLayout(null);
		
		chatRoomListScroll.setBounds(5,5,390,520);
		addChatRoom.setBounds(5, 525, 390, 50);
		panel.setBounds(0, 0, 400, 600);
		
		add(panel);
	}
	
	public void addChatRoomListener(ActionListener e) {
		addChatRoom.addActionListener(e);
	}
	
	public void addCRLWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}

	@Override
	public void appendChat(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appendChat(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearChat() {
		// TODO Auto-generated method stub
		
	}

}
