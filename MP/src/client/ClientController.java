package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientController {

	private Client model;
	private ClientView view;

	public ClientController(Client model, ClientView view) {
		this.model = model;
		this.view = view;

		// view.addActionController(new ActionController());
	}
	
	public void init() {
		this.view.addLoginListener(new LoginListener());
		this.view.addLogoutListener(new LogoutListener());
		this.view.addSendMessageListener(new SendMessageListener());
	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getUserName().getText().isEmpty()) {
				view.getLogin().setEnabled(false);
				view.getUserName().setEnabled(false);
				//asking server if username is ok
				//registering user socket
				
				try {
					model.openSocket();
				} catch (IOException ex) {}
				
				
				view.getLogout().setEnabled(true);
				view.getMessage().setEnabled(true);
				view.getSendMessage().setEnabled(true);
			}
		}

	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getLogout().setEnabled(false);
			//disconnect from the server
			view.getUserName().setEnabled(true);
			view.getLogin().setEnabled(true);
			view.getMessage().setEnabled(false);
			view.getSendMessage().setEnabled(false);
		}

	}

	class SendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

}
