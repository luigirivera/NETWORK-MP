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
		}

	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

	class SendMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

}
