package client.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.controller.ClientController;
import client.model.ClientModel;
import message.ChatMessage;
import message.Message;
import message.UsernameListMessage;
import message.content.UsernameList;
import message.utility.MessageScope;

public class ClientGroupView extends JFrame implements ClientObserver{

	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";
	
	private ClientModel model;
	private ClientController controller;
	private ClientGroupView view;
	private String destination;

	private HTMLTextPane chat;
	private JButton invite;
	private JButton sendMessage;
	private JButton sendFile;
	private JButton leave;
	private JTextField message;
	private JList<String> members;
	private JScrollPane membersScroll;
	private JScrollPane chatScroll;
	private JScrollPane messageScroll;
	private JPopupMenu dmMenu;
	private JMenuItem privateDM;
	private JMenuItem createGroupDM;
	private JPanel panel;
	private DefaultListModel<String> membersList;

	public ClientGroupView(ClientModel model, ClientController controller, String destination) {
		super("Group DM - MonoChrome");

		this.model = model;
		this.controller = controller;
		this.destination = destination;
		this.view = this;

		/*if (model.getSystemOS().equals("Windows"))
			this.setSize(770, 525);
		else if (model.getSystemOS().equals("Mac"))
			this.setSize(750, 500);
		else
			this.setSize(770, 525);*/
		
		this.setSize(750, 500);
		
		ImageIcon icon = new ImageIcon("res/mc_icon.png");
		setIconImage(icon.getImage());

		this.init();
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		this.initListeners();
	}

	// ------------INITIALIZER------------//

	private void init() {
		panel = new JPanel();
		chat = new HTMLTextPane();
		invite = new JButton("+ Invite");
		sendMessage = new JButton("Send");
		sendFile = new JButton("...");
		membersList = new DefaultListModel<String>();
		members = new JList<String>(membersList);
		message = new JTextField();
		membersScroll = new JScrollPane();
		chatScroll = new JScrollPane();
		messageScroll = new JScrollPane();
		leave = new JButton("Leave Group DM");
		dmMenu = new JPopupMenu();
		privateDM = new JMenuItem("Message");
		createGroupDM = new JMenuItem("Group Message");

		membersScroll.setViewportView(members);
		chatScroll.setViewportView(chat);
		messageScroll.setViewportView(message);

		dmMenu.add(privateDM);
		dmMenu.add(createGroupDM);

		panel.setLayout(null);
		panel.add(chatScroll);
		panel.add(membersScroll);
		panel.add(messageScroll);
		panel.add(sendMessage);
		panel.add(sendFile);
		panel.add(invite);
		panel.add(leave);

		add(panel);
		panel.setBounds(0, 0, 750, 500);
		chatScroll.setBounds(5, 5, 550, 400);
		messageScroll.setBounds(5, 425, 550, 50);
		sendMessage.setBounds(560, 425, 120, 50);
		sendFile.setBounds(690, 425, 50, 50);
		invite.setBounds(560, 5, 175, 40);
		membersScroll.setBounds(560, 50, 175, 300);
		leave.setBounds(560, 360, 175, 50);
	}
	
	private void initListeners() {
		this.addViewWindowListener(new ViewWindowListener());
		this.addInviteListener(new InviteListener());
		this.addMessageBoxKeyListener(new MessageBoxKeyListener());
		this.addSendMessageListener(new SendMessageListener());
		this.addSendFileListener(new SendFileListener());
		this.addLeaveListener(new LeaveListener());
	}

	// ------------LISTENER ADDERS------------//
	
	public void addViewWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}

	public void addInviteListener(ActionListener e) {
		invite.addActionListener(e);
	}

	public void addMessageBoxKeyListener(KeyListener e) {
		message.addKeyListener(e);
	}

	public void addSendMessageListener(ActionListener e) {
		sendMessage.addActionListener(e);
	}

	public void addSendFileListener(ActionListener e) {
		sendFile.addActionListener(e);
	}

	public void addMessageMenuListener(ActionListener e) {
		privateDM.addActionListener(e);
	}

	public void addGroupMessageMenuListener(ActionListener e) {
		createGroupDM.addActionListener(e);

	}
	
	public void addLeaveListener(ActionListener e) {
		leave.addActionListener(e);
	}
	
	// ------------LISTENER CLASSES------------//
	
	class ViewWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			controller.leaveGroup(destination);
			model.detach(view);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			controller.leaveGroup(destination);
			model.detach(view);
		}
		
		
	}
	
	class InviteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel();
			
			JTextField field = new JTextField();
			field.setPreferredSize(new Dimension(100, 25));
			
			panel.add(field);
			
			int result = JOptionPane.showConfirmDialog(null, panel, "Invite User", JOptionPane.OK_CANCEL_OPTION);
			
			switch(result) {
			case JOptionPane.OK_OPTION:
				if (!field.getText().trim().isEmpty())
					controller.inviteToGroup(destination, field.getText());
				break;
			default:
				break;
			}
		}
		
	}
	
	class MessageBoxKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				if (!message.getText().trim().isEmpty()) {
					controller.sendGroup(destination, message.getText());
					message.setText("");
				}
			}
		}
		
	}
	
	class SendMessageListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!message.getText().trim().isEmpty()) {
				controller.sendGroup(destination, message.getText());
				message.setText("");
			}
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
					controller.sendGroup(destination, file);
					if (!message.getText().trim().isEmpty()) {
						controller.sendGroup(destination, message.getText().trim());
					}
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	class LeaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			leave.setEnabled(false);
			leave.setVisible(false);
			controller.leaveGroup(destination);
			model.detach(view);
			view.setVisible(false);
			view.dispose();
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
		if(last.getScope()==MessageScope.GROUP && last.getDestination().equalsIgnoreCase(this.destination)) {
			if (last instanceof UsernameListMessage)
				this.updateUsernameList(((UsernameListMessage)last).getContent());
			else if (last instanceof ChatMessage)
				chat.appendChat(model.getLast());
		}
	}
	
	public void updateUsernameList(UsernameList usernames) {
		membersList.clear();
		for (String str : usernames) {
			membersList.addElement(str);
		}
	}
	
	// ------------MISC------------//
	
	public String getDestination() {
		return this.destination;
	}
}
