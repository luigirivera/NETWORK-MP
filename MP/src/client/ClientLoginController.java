package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class ClientLoginController {
	private ClientLoginView view;
	private Client model;
	
	private String usernamePlaceholder = "Username";
	
	public ClientLoginController(Client model, ClientLoginView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		this.view.addLoginListener(new LoginListener());
		this.view.addLoginBoxListener(new LoginBoxKeyListener(), new LoginBoxFocusListener());
	}
	
	class LoginBoxFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(view.getUserName().getText().equals(usernamePlaceholder)) {
				view.getUserName().setText("");
				view.getUserName().setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(view.getUserName().getText().equals("")) {
				view.getUserName().setText(usernamePlaceholder);
				view.getUserName().setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class LoginBoxKeyListener implements KeyListener{

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
		int port = 0;
		
		try {
			port = Integer.parseInt(view.getPort().getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter an Integer for the Port", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		if(!view.getUserName().getText().equals(usernamePlaceholder) && port>0) {
			try {
				model.openSocket(view.getHost().getText(), port);
				model.setName(view.getUserName().getText());
				model.sendMessage(view.getUserName().getText());
				view.setVisible(false);
				model.getGlobalView().setVisible(true);
			} catch (IOException ex) { 
				ex.printStackTrace(); 
				JOptionPane.showMessageDialog(null, "Server unavailable", "Error", JOptionPane.ERROR_MESSAGE);
			} 
		}
	}
}
