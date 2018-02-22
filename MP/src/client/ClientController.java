package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			view.getLogin().setEnabled(false);
			view.getUserName().setEnabled(false);
			view.getLogout().setEnabled(true);
			view.getMessage().setEnabled(true);
			view.getSendMessage().setEnabled(true);
		}

	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.getLogin().setEnabled(true);
			view.getUserName().setEnabled(true);
			view.getLogout().setEnabled(false);
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
