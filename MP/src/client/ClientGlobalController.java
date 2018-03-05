package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientGlobalController {

	private Client model;
	private ClientGlobalView view;
	
	private String messagePlaceholderName = "Message";
	private String DEFAULT_HOST = "localhost";
	private String DEFAULT_PORT = "5000";

	public ClientGlobalController(Client model, ClientGlobalView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		this.view.addLogoutListener(new LogoutListener());
		this.view.addSendMessageListener(new SendMessageListener());
		this.view.addMessageBoxListener(new MessageBoxKeyListener(), new MessageBoxFocusListener());
		this.view.addUserListListener(new UserListListener());
		this.view.addShowChatRoomsListener(new ShowChatRoomsListener());
		this.view.addHideChatRoomsListener(new HideChatRoomsListener());
		this.view.addMessageMenuListener(new MessageMenuListener());
		this.view.addGroupMessageMenuListener(new GroupMessageMenuListener());
		this.view.addSendFileListener(new SendFileListener());
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
	
	class ShowChatRoomsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.getShowChatRoom().setVisible(false);
			view.getHideChatRoom().setVisible(true);
			model.getChatroomListView().setVisible(true);
		}
		
	}
	
	class HideChatRoomsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.getShowChatRoom().setVisible(true);
			view.getHideChatRoom().setVisible(false);
			model.getChatroomListView().setVisible(false);
		}
		
	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setVisible(false);
			//hide windows and close all DMs and chat rooms
			model.getLoginView().setVisible(true);
			try {
				model.closeSocket();
			} catch (IOException ex) { ex.printStackTrace(); }
			view.clearChat();
		}

	}

	class SendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getMessage().getText().isEmpty() && !view.getMessage().getText().equals(messagePlaceholderName)) {
				try {
					model.sendMessage(view.getMessage().getText());
				} catch (IOException ex) {ex.printStackTrace();}
				view.getMessage().setText(messagePlaceholderName);
				view.getMessage().setForeground(Color.GRAY);
			}
		}

	}
	
	class MessageBoxKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && !view.getMessage().getText().isEmpty()) {
				try {
					model.sendMessage(view.getMessage().getText());
				} catch (IOException ex) {ex.printStackTrace();}
				view.getMessage().setText("");				
			}
			else {
				JScrollBar h = view.getMessageScroll().getHorizontalScrollBar();
				h.setValue(h.getMaximum());	
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	class MessageBoxFocusListener implements FocusListener{
		@Override
		public void focusGained(FocusEvent arg0) {
			if (view.getMessage().getText().equals(messagePlaceholderName)) {
				view.getMessage().setText("");
				view.getMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getMessage().getText().isEmpty()) {
				view.getMessage().setForeground(Color.GRAY);
				view.getMessage().setText(messagePlaceholderName);
			}
			
		}
		
	}
	
	class UserListListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e))
				if(!view.getUserList().getSelectedValue().equalsIgnoreCase(model.getName()) && view.getUserList().getSelectedIndices().length == 1
				|| view.getUserList().getSelectedValuesList().contains(model.getName()) && view.getUserList().getSelectedIndices().length == 2)
				{
					view.getCreateGroupDM().setEnabled(false);
					view.getPrivateDM().setEnabled(true);
					view.getDmMenu().show(view.getUserList(), e.getX(), e.getY());
				}
			
				else if(view.getUserList().getSelectedValue().equalsIgnoreCase(model.getName()) && view.getUserList().getSelectedIndices().length == 1);
			
				else {
					view.getCreateGroupDM().setEnabled(true);
					view.getPrivateDM().setEnabled(false);
					view.getDmMenu().show(view.getUserList(), e.getX(), e.getY());
				}
			else if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && !view.getUserList().getSelectedValue().equalsIgnoreCase(model.getName())) {
				ClientDMView newView = new ClientDMView(model, view.getUserList().getSelectedValue());
				ClientDMController newController = new ClientDMController(model, newView);
				newController.init();
				model.attach(newView);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class MessageMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ClientDMView newView = new ClientDMView(model, view.getUserList().getSelectedValue());
			ClientDMController newController = new ClientDMController(model, newView);
			newController.init();
			model.attach(newView);
			
		}
		
	}
	
	class GroupMessageMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//dm view stuff
		}
		
	}

}
