package client;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import message.Message;

public class ClientChatRoomListView extends JFrame{
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

		if (model.getSystemOS().equals("Windows"))
			setSize(420, 625);
		else if (model.getSystemOS().equals("Mac"))
			setSize(400, 600);
		else
			setSize(420, 625);

		setLayout(null);
		setResizable(false);
		setVisible(false);
	}

	// ------------INITIALIZER------------//

	private void init() {
		chatRoomListScroll = new JScrollPane();
		chatRoomsList = new DefaultListModel<String>();
		chatRoomList = new JList<String>(chatRoomsList);
		addChatRoom = new JButton("+ Create Chat Room");
		panel = new JPanel();

		chatRoomListScroll.setViewportView(chatRoomList);
		chatRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panel.add(chatRoomListScroll);
		panel.add(addChatRoom);
		chatRoomsList.addElement("Hello");
		addChatRoom.setMargin(new Insets(0, 0, 0, 0));
		panel.setLayout(null);

		chatRoomListScroll.setBounds(5, 5, 390, 520);
		addChatRoom.setBounds(5, 525, 390, 50);
		panel.setBounds(0, 0, 400, 600);

		add(panel);
	}

	// ------------LISTENERS------------//
	public void addChatRoomListener(ActionListener e) {
		addChatRoom.addActionListener(e);
	}

	public void addCRLWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}

	public void addCRLListener(MouseListener e) {
		chatRoomList.addMouseListener(e);
	}

	// ------------UPDATE METHODS------------//
	

	// ------------GETTERS AND SETTERS------------//
	public JList<String> getChatRoomList() {
		return chatRoomList;
	}

	public void setChatRoomList(JList<String> chatRoomList) {
		this.chatRoomList = chatRoomList;
	}

	public JButton getAddChatRoom() {
		return addChatRoom;
	}

	public void setAddChatRoom(JButton addChatRoom) {
		this.addChatRoom = addChatRoom;
	}
}