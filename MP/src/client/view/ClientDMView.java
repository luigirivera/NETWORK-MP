package client.view;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.controller.ClientController;
import client.model.ClientModel;
import message.ChatMessage;
import message.Message;
import message.UsernameListMessage;
import message.utility.MessageScope;

public class ClientDMView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private static final String messagePlaceholderName = "Message";

	private ClientModel model;
	private ClientController controller;
	private ClientDMView view;
	private String destination;

	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private HTMLTextPane dmChat;
	private JScrollPane dmChatScroll;
	private JScrollPane dmMessageScroll;
	private JButton dmSendFile;

	public ClientDMView(ClientModel model, ClientController controller, String destination) {
		super(String.format("%s - MonoChrome", destination));

		this.model = model;
		this.controller = controller;
		this.view = this;
		this.destination = destination;

		init();
		
		/*if(model.getSystemOS().equals("Windows"))
			this.setSize(520, 545);
		else if(model.getSystemOS().equals("Mac"))
			this.setSize(500, 520);
		else
			this.setSize(520, 545);*/
		this.setSize(500, 520);
		
		ImageIcon icon = new ImageIcon("res/mc_icon.png");
		setIconImage(icon.getImage());

		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		this.initListeners();
	}

	// ------------INITIALIZER------------//

	public void init() {
		dmPanel = new JPanel();
		dmMessage = new JTextField();
		dmSend = new JButton("Send");
		dmChat = new HTMLTextPane();
		dmChatScroll = new JScrollPane();
		dmMessageScroll = new JScrollPane();
		dmSendFile = new JButton("...");

		dmChatScroll.setViewportView(dmChat);
		dmMessageScroll.setViewportView(dmMessage);
		
		dmPanel.setLayout(null);
		dmPanel.add(dmChatScroll);
		dmPanel.add(dmMessageScroll);
		dmPanel.add(dmSend);
		dmPanel.add(dmSendFile);
		this.add(dmPanel);
		
		dmChatScroll.setBounds(5,5,480,420);
		dmMessageScroll.setBounds(5,430,350,50);
		dmSend.setBounds(360, 430, 75, 50);
		dmSendFile.setBounds(440, 430, 50, 50);
		dmPanel.setBounds(0, 0, 500, 520);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initListeners() {
		this.addViewWindowListener(new ViewWindowListener());
		this.addDMSendMessageListener(new DMSendMessageListener());
		this.addDMMessageBoxKeyListener(new DMMessageBoxKeyListener());
		this.addDMSendFileListener(new DMSendFileListener());
	}

	// ------------LISTENER ADDERS------------//
	public void addViewWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addDMSendMessageListener(ActionListener e) {
		dmSend.addActionListener(e);
	}

	public void addDMMessageBoxKeyListener(KeyListener e) {
		dmMessage.addKeyListener(e);
	}
	
	public void addDMSendFileListener(ActionListener e) {
		dmSendFile.addActionListener(e);
	}
	
	// ------------LISTENER CLASSES------------//
	class ViewWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			model.detach(view);
		}

		@Override
		public void windowClosed(WindowEvent e) {
			super.windowClosed(e);
			model.detach(view);
		}
		
	}
	
	class DMSendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!dmMessage.getText().trim().isEmpty()) {
				controller.sendDirect(destination, dmMessage.getText());
				dmMessage.setText("");
			}
		}
		
	}
	
	class DMMessageBoxKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				if (!dmMessage.getText().trim().isEmpty()) {
					controller.sendDirect(destination, dmMessage.getText());
					dmMessage.setText("");
				}
			}
		}
		
	}
	
	class DMSendFileListener implements ActionListener {

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
					controller.sendDirect(destination, file);
					if (!message.getText().trim().isEmpty()) {
						controller.sendDirect(destination, message.getText().trim());
					}
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	// ------------LISTENER CLASSES------------//
	@Override
	public void update() {
		if (!controller.isConnected()) {
			model.detach(this);
			this.setVisible(false);
			this.dispose();
			return;
		}
		
		Message<?> last = model.getLast();
		if(last.getScope()==MessageScope.GLOBAL 
				&& last instanceof UsernameListMessage
				&& !((UsernameListMessage)last).getContent().contains(this.destination)) {
			model.detach(this);
			dmChat.appendChat(String.format("%s has disconnected", this.destination));
			this.dmSend.setEnabled(false);
			this.dmSendFile.setEnabled(false);
		} 
		else if (last instanceof ChatMessage && last.getScope()==MessageScope.DIRECT
				&& (last.getSource().equalsIgnoreCase(this.destination) 
				|| last.getDestination().equalsIgnoreCase(this.destination))) {
			dmChat.appendChat(last);
		}
	}
	
	// ------------MISC------------//
	public String getDestination() {
		return this.destination;
	}
}
