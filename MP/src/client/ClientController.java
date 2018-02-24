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
		this.view.addUserListListener(new UserListListener());
		
		this.view.addLoginBoxListener(new LoginBoxListener());
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
			view.getLogin().setEnabled(false);
			view.getUserName().setEnabled(false);
			try {
				model.openSocket();
				model.setName(view.getUserName().getText());
				model.sendMessage(view.getUserName().getText());
			} catch (IOException ex) { ex.printStackTrace(); }
			view.getLogout().setEnabled(true);
			view.getMessage().setEnabled(true);
			view.getSendMessage().setEnabled(true);
			view.getUserList().setVisible(true);
			view.getChat().setVisible(true);
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
			if(!view.getMessage().getText().isEmpty() && !view.getMessage().getText().equals(placeholderName)) {
				try {
					model.sendMessage(view.getMessage().getText());
				} catch (IOException ex) {ex.printStackTrace();}
				view.getMessage().setText(placeholderName);
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
	
	class UserListListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2 && !view.getUserList().getSelectedValue().equalsIgnoreCase(model.getName())) {
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

}
