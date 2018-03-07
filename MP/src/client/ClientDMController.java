package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

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
		this.view.addDMWindowListener(new DMWindowListener());
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
				view.getDmMessage().setForeground(Color.BLACK);
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
			try {
				model.sendMessage(view.getDmMessage().getText(), view.getDestUser());
			} catch (IOException e) { e.printStackTrace(); }
			view.getDmMessage().setText("");
			view.getDmMessage().setForeground(Color.GRAY);
		}
	}
	
	class DMWindowListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {
			model.detach(view);
		}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}
		
	}

}
