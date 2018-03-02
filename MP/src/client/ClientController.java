package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ClientController {

	private Client model;
	private ClientView view;
	
	private String messagePlaceholderName = "Message";
	private String DEFAULT_HOST = "localhost";
	private String DEFAULT_PORT = "5000";

	public ClientController(Client model, ClientView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		this.view.addLoginListener(new LoginListener());
		this.view.addLogoutListener(new LogoutListener());
		this.view.addSendMessageListener(new SendMessageListener());
		this.view.addMessageBoxListener(new MessageBoxKeyListener(), new MessageBoxFocusListener());
		this.view.addUserListListener(new UserListListener());
		this.view.addShowChatRoomsListener(new ShowChatRoomsListener());
		this.view.addHideChatRoomsListener(new HideChatRoomsListener());
		
		this.view.addLoginBoxListener(new LoginBoxListener());
	}
	
	class ShowChatRoomsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.getShowChatRoom().setVisible(false);
			view.getHideChatRoom().setVisible(true);
			//show JFrame of Chatrooms
		}
		
	}
	
	class HideChatRoomsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.getShowChatRoom().setVisible(true);
			view.getHideChatRoom().setVisible(false);
			//hide JFrame of Chatrooms
		}
		
	}
	
	class LoginBoxListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				onLogin();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			onLogin();
		}

	}
	
	private void onLogin() {
		if(!view.getUserName().getText().isEmpty()) {
			view.getLogin().setVisible(false);
			view.getUserName().setEnabled(false);
			try {
				model.openSocket();
				model.setName(view.getUserName().getText());
				model.sendMessage(view.getUserName().getText());
			} catch (IOException ex) { ex.printStackTrace(); }
			view.getLogout().setVisible(true);
			view.getMessage().setEnabled(true);
			view.getSendMessage().setEnabled(true);
			view.getUserList().setVisible(true);
			view.getChat().setVisible(true);
			view.getHost().setEnabled(false);
			view.getPort().setEnabled(false);
			view.getShowChatRoom().setEnabled(true);
			view.getHideChatRoom().setEnabled(true);
			view.getCreateGroupDM().setEnabled(false);
			view.getSendFile().setEnabled(true);
		}
	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getLogout().setVisible(false);
			try {
				model.closeSocket();
			} catch (IOException ex) { ex.printStackTrace(); }
			view.getUserName().setEnabled(true);
			view.getLogin().setVisible(true);
			view.getMessage().setEnabled(false);
			view.getSendMessage().setEnabled(false);
			view.getUserList().setVisible(false);
			view.getChat().setVisible(false);
			view.getHost().setEnabled(true);
			view.getPort().setEnabled(true);
			view.getShowChatRoom().setEnabled(false);
			view.getHideChatRoom().setEnabled(false);
			view.getHideChatRoom().setVisible(false);
			view.getShowChatRoom().setVisible(true);
			view.getCreateGroupDM().setEnabled(false);
			view.getSendFile().setEnabled(false);
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
			if(e.getClickCount() == 2 && !view.getUserList().getSelectedValue().equalsIgnoreCase(model.getName()) && view.getUserList().getSelectedIndices().length == 1) {
				ClientDMView newView = new ClientDMView(model, view.getUserList().getSelectedValue());
				ClientDMController newController = new ClientDMController(model, newView);
				newController.init();
				model.attach(newView);
			}
			
			else if(view.getUserList().getSelectedIndices().length > 1) {
				if(view.getUserList().getSelectedIndices().length == 2 && !view.getUserList().getSelectedValuesList().contains(model.getName()))
					view.getCreateGroupDM().setEnabled(true);
				else if(view.getUserList().getSelectedIndices().length > 2)
					view.getCreateGroupDM().setEnabled(true);
				else
					view.getCreateGroupDM().setEnabled(false);
			}
			
			else if (view.getUserList().getSelectedIndices().length < 2 || view.getUserList().getSelectedIndices().length == 2 && view.getUserList().getSelectedValuesList().contains(model.getName())) {
				view.getCreateGroupDM().setEnabled(false);
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

}
