package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ClientGroupDMController {
	private Client model;
	private ClientGroupDMView view;

	public ClientGroupDMController(Client model, ClientGroupDMView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		view.addInviteListener(new InviteListener());
		view.addMessageBoxListener(new MessageBoxKeyListener(), new MessageBoxFocusListener());
		view.addSendMessageListener(new SendMessageListener());
		view.addSendFileListener(new SendFileListener());
	}
	
	class SendFileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
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
					//send file
					break;
				default:
					break;
				}
			}
		}
		
	}
	
	class InviteListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JPanel panel = new JPanel();
			JComboBox<String> nonMembers = new JComboBox<String>();
			
			nonMembers.addItem("List of Users here");
			
			panel.add(nonMembers);
			
			int result = JOptionPane.showConfirmDialog(null, panel, "Invite User", JOptionPane.OK_CANCEL_OPTION);
			
			switch(result) {
			case JOptionPane.OK_OPTION:
				//add friend to DM
				break;
			default:
				break;
			}
		}
		
	}
	
	class MessageBoxFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent arg0) {
			if (view.getMessage().getText().equals(ClientGroupDMView.getMessageplaceholdername())) {
				view.getMessage().setText("");
				view.getMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getMessage().getText().isEmpty()) {
				view.getMessage().setForeground(Color.GRAY);
				view.getMessage().setText(ClientGroupDMView.getMessageplaceholdername());
			}

		}
	}

	class MessageBoxKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessage();
				view.getMessage().setForeground(Color.BLACK);
			}
			else {
				JScrollBar h = view.getMembersScroll().getHorizontalScrollBar();
				h.setValue(h.getMaximum());	
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

	class SendMessageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
			view.getMessage().setText(ClientGroupDMView.getMessageplaceholdername());
			view.getMessage().setForeground(Color.GRAY);
		}
	}

	private void sendMessage() {
		if (!view.getMessage().getText().isEmpty()
				&& !view.getMessage().getText().equals(ClientGroupDMView.getMessageplaceholdername())) {
			try {
				model.sendMessage(view.getMessage().getText(), view.getDestUser());
			} catch (IOException e) { e.printStackTrace(); }
			view.getMessage().setText("");
			view.getMessage().setForeground(Color.GRAY);
		}
	}
}
