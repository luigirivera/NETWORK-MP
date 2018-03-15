package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
		this.view.addDmSendFileListener(new SendFileListener());
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

	class DMMessageBoxFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent arg0) {
			if (view.getDmMessage().getText().equals(ClientDMView.getMessagePlaceholdername())) {
				view.getDmMessage().setText("");
				view.getDmMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getDmMessage().getText().isEmpty()) {
				view.getDmMessage().setForeground(Color.GRAY);
				view.getDmMessage().setText(ClientDMView.getMessagePlaceholdername());
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
			else {
				JScrollBar h = view.getDmMessageScroll().getHorizontalScrollBar();
				h.setValue(h.getMaximum());	
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
			view.getDmMessage().setText(ClientDMView.getMessagePlaceholdername());
		}
	}

	private void sendMessage() {
		if (!view.getDmMessage().getText().isEmpty()
				&& !view.getDmMessage().getText().equals(ClientDMView.getMessagePlaceholdername())) {
			try {
				model.sendMessage(view.getDmMessage().getText(), view.getDestination());
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
			model.getChatViews().remove(view);
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
