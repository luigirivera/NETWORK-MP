package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import shared.DirectMessage;

public class ClientDMController {
	private Client model;
	private ClientDMView view;

	public ClientDMController(Client model, ClientDMView view) {
		this.model = model;
		this.view = view;
	}

	public void init() {
		this.view.addDMMessageBoxListener(new DMMessageBoxKeyListener(), new DMMessageBoxFocusListener());
		this.view.addDMSendMessageListener(new DMSendMessageListener());
	}

	class DMMessageBoxFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent arg0) {
			if (view.getDmMessage().getText().equals(ClientDMView.getPlaceholdername())) {
				view.getDmMessage().setText("");
				view.getDmMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getDmMessage().getText().isEmpty()) {
				view.getDmMessage().setForeground(Color.GRAY);
				view.getDmMessage().setText(ClientDMView.getPlaceholdername());
			}

		}
	}

	class DMMessageBoxKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessage();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}

	class DMSendMessageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
		}
	}

	private void sendMessage() {
		if (!view.getDmMessage().getText().isEmpty()
				&& !view.getDmMessage().getText().equals(ClientDMView.getPlaceholdername())) {
			DirectMessage msg = new DirectMessage();
			try {
				model.sendMessage(view.getDmMessage().getText(), view.getDestUser());
			} catch (IOException e) { e.printStackTrace(); }
			view.getDmMessage().setText("");
			view.getDmMessage().setForeground(Color.GRAY);
		}
	}

}
