package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class ClientController {

	private Client model;
	private ClientView view;
	
	private String placeholderName = "Message";

	public ClientController(Client model, ClientView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		this.view.addLoginListener(new LoginListener());
		this.view.addLogoutListener(new LogoutListener());
		this.view.addSendMessageListener(new SendMessageListener());
		this.view.addMessageBoxListener(new MessageBoxKeyListener(), new MessageBoxFocusListener());
		this.view.addLoginBoxListener(new LoginBoxListener());
	}
	
	class LoginBoxListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && !view.getUserName().getText().isEmpty()) {
				view.getLogin().setEnabled(false);
				view.getUserName().setEnabled(false);
				//asking server if username is ok
				//registering user socket
				//adding the user to the usernameList
				try {
					model.openSocket();
				} catch (IOException ex) {}
				
				
				view.getLogout().setEnabled(true);
				view.getMessage().setEnabled(true);
				view.getSendMessage().setEnabled(true);
				view.getUserList().setVisible(true);
				view.getChat().setVisible(true);				
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
			if(!view.getUserName().getText().isEmpty()) {
				view.getLogin().setEnabled(false);
				view.getUserName().setEnabled(false);
				//asking server if username is ok
				//registering user socket
				//adding the user to the usernameList
				try {
					model.openSocket();
					model.sendMessage(view.getUserName().getText());
				} catch (IOException ex) { ex.printStackTrace(); }
				
				
				view.getLogout().setEnabled(true);
				view.getMessage().setEnabled(true);
				view.getSendMessage().setEnabled(true);
				view.getUserList().setVisible(true);
				view.getChat().setVisible(true);
			}
		}

	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getLogout().setEnabled(false);
			try {
				model.closeSocket();
			} catch (IOException ex) { ex.printStackTrace(); }
			view.getUserName().setEnabled(true);
			view.getLogin().setEnabled(true);
			view.getMessage().setEnabled(false);
			view.getSendMessage().setEnabled(false);
			view.getUserList().setVisible(false);
			view.getChat().setVisible(false);
		}

	}

	class SendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getMessage().getText().isEmpty()) {
				//push the message text
				view.getMessage().setText(placeholderName);
				view.getMessage().setForeground(Color.GRAY);
			}
		}

	}
	
	class MessageBoxKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && !view.getMessage().getText().isEmpty()) {
				//push the message text
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
			if (view.getMessage().getText().equals(placeholderName)) {
				view.getMessage().setText("");
				view.getMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getMessage().getText().isEmpty()) {
				view.getMessage().setForeground(Color.GRAY);
				view.getMessage().setText(placeholderName);
			}
			
		}
		
	}

}
