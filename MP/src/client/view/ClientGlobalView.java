package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import client.controller.ClientController;
import client.model.ClientModel;
import message.ChatMessage;
import message.ChatRoomInfoListMessage;
import message.Message;
import message.UsernameListMessage;
import message.content.ChatRoomInfo;
import message.content.ChatRoomInfoList;
import message.content.UsernameList;
import message.utility.MessageScope;

public class ClientGlobalView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private String placeholderName = "Message";

	private ClientModel model;
	private ClientController controller;
	private ClientGlobalView view;

	private JList<String> userList;
	private JTextField message;
	// private JTextArea chat;
	private HTMLTextPane chat;
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
	
	private ClientChatRoomListView chatroomListView;

	public ClientGlobalView(ClientModel model, ClientController controller) {
		super("MonoChrome");

		this.model = model;
		this.controller = controller;
		this.view = this;

		init();

		/*
		 * if(model.getSystemOS().equals("Windows")) setSize(770, 600); else
		 * if(model.getSystemOS().equals("Mac")) setSize(750, 575); else setSize(770,
		 * 600);
		 */
		
		setSize(750, 575);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setLayout(null);
		setVisible(true);
		setResizable(false);
		
		initListeners();
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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.chatroomListView = new ClientChatRoomListView(this.model, this.controller);
	}

	private void chatPanelInit() {
		message = new JTextField();
		// chat = new JTextArea("");
		chat = new HTMLTextPane();
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

		dmMenu.add(privateDM);
		dmMenu.add(createGroupDM);

		chat.setEditable(false);
		// chat.setLineWrap(true);

		showChatRoom.setMargin(new Insets(0, 0, 0, 0));
		logout.setMargin(new Insets(0, 0, 0, 0));

		chatPanel.setLayout(null);
		chatPanel.add(messageScroll);
		chatPanel.add(showChatRoom);
		chatPanel.add(hideChatRoom);
		chatPanel.add(logout);
		chatPanel.add(chatScroll);
		chatPanel.add(userListScroll);
		chatPanel.add(sendMessage);
		chatPanel.add(sendFile);

		chatScroll.setBounds(5, 5, 550, 400);
		showChatRoom.setBounds(560, 5, 175, 40);
		hideChatRoom.setBounds(560, 5, 175, 40);
		userListScroll.setBounds(560, 50, 175, 300);
		logout.setBounds(560, 360, 175, 40);
		messageScroll.setBounds(5, 410, 550, 50);
		sendMessage.setBounds(560, 410, 125, 50);
		sendFile.setBounds(690, 410, 45, 50);

	}
	
	public void initListeners() {
		this.addViewWindowListener(new ViewWindowListener());
		this.addChatroomListViewWindowListener(new ChatroomListViewWindowListener());
		this.addLogoutListener(new LogoutListener());
		this.addSendMessageListener(new SendMessageListener());
		this.addUserListListener(new UserListListener());
		this.addShowChatRoomsListener(new ShowChatRoomsListener());
		this.addHideChatRoomsListener(new HideChatRoomsListener());
		this.addMessageMenuListener(new MessageMenuListener());
		this.addMessageBoxKeyListener(new MessageBoxKeyListener());
		this.addGroupMessageMenuListener(new GroupMessageMenuListener());
		this.addSendFileListener(new SendFileListener());
	}

	// ------------LISTENER ADDERS------------//
	
	public void addViewWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addChatroomListViewWindowListener(WindowListener e) {
		chatroomListView.addWindowListener(e);
	}

	public void addLogoutListener(ActionListener e) {
		logout.addActionListener(e);
	}

	public void addSendMessageListener(ActionListener e) {
		sendMessage.addActionListener(e);
	}

	public void addMessageBoxKeyListener(KeyListener e) {
		message.addKeyListener(e);
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

	public void addGroupMessageMenuListener(ActionListener e) {
		createGroupDM.addActionListener(e);

	}

	public void addSendFileListener(ActionListener e) {
		sendFile.addActionListener(e);
	}

	// ------------LISTENER CLASSES------------//
	
	class ViewWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			try {
				controller.closeSocket();
				model.detach(view);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			try {
				controller.closeSocket();
				model.detach(view);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
	}
	
	class ChatroomListViewWindowListener extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			view.hideChatRoom.setEnabled(false);
			view.hideChatRoom.setVisible(false);
			view.showChatRoom.setVisible(true);
			view.showChatRoom.setEnabled(true);
		}
		
	}
	
	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				controller.closeSocket();
				model.detach(view);
				view.setVisible(false);
				view.dispose();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	class SendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!message.getText().trim().isEmpty()) {
				controller.sendGlobal(message.getText());
				message.setText("");
			}
		}

	}
	
	class MessageBoxKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				if (!message.getText().trim().isEmpty()) {
					controller.sendGlobal(message.getText());
					message.setText("");
				}
			}
		}
		
	}
	
	class UserListListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if(SwingUtilities.isRightMouseButton(e)) {
				if(!userList.getSelectedValue().equalsIgnoreCase(model.getUsername()) && userList.getSelectedIndices().length == 1
				|| userList.getSelectedValuesList().contains(model.getUsername()) && userList.getSelectedIndices().length == 2)
				{
					createGroupDM.setEnabled(false);
					privateDM.setEnabled(true);
					dmMenu.show(userList, e.getX(), e.getY());
				}
			
				else if(userList.getSelectedValue().equalsIgnoreCase(model.getUsername()) && userList.getSelectedIndices().length == 1);
			
				else {
					createGroupDM.setEnabled(true);
					privateDM.setEnabled(false);
					dmMenu.show(userList, e.getX(), e.getY());
				}
			}
			else if(SwingUtilities.isLeftMouseButton(e) 
					&& e.getClickCount() == 2 && !userList.getSelectedValue().equalsIgnoreCase(model.getUsername())) {
				controller.openDMWindow(userList.getSelectedValue());
			}
		}
		
	}
	
	class ShowChatRoomsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.showChatRoom.setEnabled(false);
			view.showChatRoom.setVisible(false);
			chatroomListView.setVisible(true);
			view.hideChatRoom.setVisible(true);
			view.hideChatRoom.setEnabled(true);
		}
		
	}
	
	class HideChatRoomsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.hideChatRoom.setEnabled(false);
			view.hideChatRoom.setVisible(false);
			chatroomListView.setVisible(false);
			view.showChatRoom.setVisible(true);
			view.showChatRoom.setEnabled(true);
		}
		
	}
	
	class MessageMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.openDMWindow(userList.getSelectedValue());
		}
		
	}
	
	class GroupMessageMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//controller open a new gdm pls
			controller.createGroup(userList.getSelectedValuesList());
		}
		
	}
	
	class SendFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showDialog(view, "Select File");
			File file = fileChooser.getSelectedFile();
			
			if(file!=null)
			{
				JPanel panel = new JPanel();
				JTextField message = new JTextField();
				JScrollPane messageScroll = new JScrollPane();
				
				messageScroll.setViewportView(message);
				message.addKeyListener(new KeyListener() {

					@Override
					public void keyPressed(KeyEvent arg0) {
						JScrollBar h = messageScroll.getHorizontalScrollBar();
						h.setValue(h.getMaximum());
					}

					@Override
					public void keyReleased(KeyEvent arg0) {}

					@Override
					public void keyTyped(KeyEvent arg0) {}
					
				});
				panel.add(messageScroll);
				
				messageScroll.setPreferredSize(new Dimension(500,50));
				int result = JOptionPane.showConfirmDialog(null, panel,String.format("Send %s", file.getName()), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
				switch(result) {
				case JOptionPane.OK_OPTION:
					controller.sendGlobal(file);
					if (!message.getText().trim().isEmpty()) {
						controller.sendGlobal(message.getText().trim());
					}
					break;
				default:
					break;
				}
			}
		}
		
	}

	// ------------UPDATE METHODS------------//
	@Override
	public void update() {
		if (!controller.isConnected()) {
			model.detach(this);
			this.setVisible(false);
			this.dispose();
			return;
		}
		
		Message<?> last = model.getLast();
		if(last.getScope()==MessageScope.GLOBAL) {
			if (last instanceof UsernameListMessage)
				this.updateUsernameList(((UsernameListMessage)last).getContent());
			else if (last instanceof ChatMessage) {
				chat.appendChat(model.getLast());
			}
		}
		this.chatroomListView.update(last);
	}

	public void updateUsernameList(UsernameList usernames) {
		usernameList.clear();
		for (String str : usernames) {
			usernameList.addElement(str);
		}
	}
}

class ClientChatRoomListView extends JFrame {
	private static final long serialVersionUID = 1L;

	private ClientModel model;
	private ClientController controller;
	private ClientChatRoomListView view;

	private JScrollPane chatRoomListScroll;
	private JList<ChatRoomInfo> roomList;
	private DefaultListModel<ChatRoomInfo> roomListModel;
	private JButton createRoom;
	private JPanel panel;

	public ClientChatRoomListView(ClientModel model, ClientController controller) {
		super("MonoChrome - Chat Rooms");

		this.model = model;
		this.controller = controller;
		this.view = this;
		this.init();

		/*if (model.getSystemOS().equals("Windows"))
			setSize(420, 625);
		else if (model.getSystemOS().equals("Mac"))
			setSize(400, 600);
		else*/
			setSize(420, 625);

		setLayout(null);
		setResizable(false);
		setVisible(false);
		
		this.initListeners();
	}

	// ------------INITIALIZER------------//

	private void init() {
		chatRoomListScroll = new JScrollPane();
		roomListModel = new DefaultListModel<ChatRoomInfo>();
		roomList = new JList<ChatRoomInfo>(roomListModel);
		createRoom = new JButton("+ Create Chat Room");
		panel = new JPanel();
		
		roomList.setCellRenderer(new RoomListListCellRenderer());

		chatRoomListScroll.setViewportView(roomList);
		roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panel.add(chatRoomListScroll);
		panel.add(createRoom);
		createRoom.setMargin(new Insets(0, 0, 0, 0));
		panel.setLayout(null);

		chatRoomListScroll.setBounds(5, 5, 390, 520);
		createRoom.setBounds(5, 525, 390, 50);
		panel.setBounds(0, 0, 400, 600);

		add(panel);
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	class RoomListListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ChatRoomInfo)
				label.setText(((ChatRoomInfo)value).getName());
			return label;
		}
		
		
		
	}
	
	private void initListeners() {
		this.addCreateRoomListener(new CreateRoomListener());
		this.addRoomListListener(new RoomListListener());
	}

	// ------------LISTENERS------------//
	public void addViewWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addCreateRoomListener(ActionListener e) {
		createRoom.addActionListener(e);
	}

	public void addRoomListListener(MouseListener e) {
		roomList.addMouseListener(e);
	}
	
	// ------------LISTENER ADDERS------------//
	
	class CreateRoomListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String crPlaceholderName = "Chat Room Name";
			String crPlaceholderPass = "Password";
			JTextField name = new JTextField(crPlaceholderName);
			JTextField pass = new JTextField(crPlaceholderPass);
			JPanel panel = new JPanel();
			
			name.setForeground(Color.GRAY);
			pass.setForeground(Color.GRAY);
			
			name.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
					if (name.getText().equals(crPlaceholderName)) {
						name.setText("");
						name.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent arg0) {
					if (name.getText().isEmpty()) {
						name.setForeground(Color.GRAY);
						name.setText(crPlaceholderName);
					}
				}
			});
			
			pass.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
					if (pass.getText().equals(crPlaceholderPass)) {
						pass.setText("");
						pass.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent arg0) {
					if (pass.getText().isEmpty()) {
						pass.setForeground(Color.GRAY);
						pass.setText(crPlaceholderPass);
					}
				}
			});
			
			panel.setLayout(new GridLayout(2,2));
			panel.add(name);
			panel.add(pass);
			int result = JOptionPane.showConfirmDialog(null, panel, "Create Chat Room", JOptionPane.OK_CANCEL_OPTION);
			
			switch (result) {
			case JOptionPane.OK_OPTION:
				String trimmedName = name.getText().trim();
				if(trimmedName.isEmpty() || trimmedName.equals(crPlaceholderName)){
					JOptionPane.showMessageDialog(null, "Please enter a Chat Room Name", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				else if(pass.getText().isEmpty() || pass.getText().equals(crPlaceholderPass)){
					JOptionPane.showMessageDialog(null, "Please enter a Chat Room Password", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					controller.createRoom(trimmedName, pass.getText());
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	class RoomListListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2)
				requestAccess(roomList.getSelectedValue().getId());
		}
		
		private void requestAccess(String id) {
			JPasswordField passField = new JPasswordField();
			JPanel panel = new JPanel();
			JLabel passLabel = new JLabel("Enter Room Password:");
			
			panel.setLayout(new GridLayout(2,2));
			panel.add(passLabel);
			panel.add(passField);
			passField.setPreferredSize(new Dimension(300,30));
			int result = JOptionPane.showConfirmDialog(null, panel,String.format("Join %s", roomList.getSelectedValue()), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			switch(result) {
			case JOptionPane.OK_OPTION:
				//attempt join
				controller.joinRoom(id, String.copyValueOf(passField.getPassword()));
				break;
			default:
				break;
			}
		}
		
	}
	
	// ------------UPDATE------------//
	
	public void update(Message<?> last) {
		if(last instanceof ChatRoomInfoListMessage) {
			this.updateRoomList(((ChatRoomInfoListMessage)last).getContent());
		}
	}

	public void updateRoomList(ChatRoomInfoList list) {
		roomListModel.clear();
		for (ChatRoomInfo info : list) {
			roomListModel.addElement(info);
		}
	}
}

