package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientChatRoomListController {
	private Client model;
	private ClientChatRoomListView view;
	
	public ClientChatRoomListController(Client model, ClientChatRoomListView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		this.view.addChatRoomListener(new CreateChatRoomListener());
		this.view.addCRLWindowListener(new ChatRoomListWindowListener());
	}
	
	class ChatRoomListWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {			
			model.getGlobalView().getHideChatRoom().setVisible(false);
			model.getGlobalView().getShowChatRoom().setVisible(true);
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}
	
	class CreateChatRoomListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String crPlaceholderName = "Chat Room Name";
			String crPlaceholderPass = "Password";
			JTextField name = new JTextField(crPlaceholderName);
			JTextField pass = new JTextField(crPlaceholderPass);
			JPanel panel = new JPanel();
			
			name.setForeground(Color.GRAY);
			pass.setForeground(Color.GRAY);
			
			name.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
					if (name.getText().equals(crPlaceholderName)) {
						name.setText("");
						name.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent arg0) {
					if (name.getText().isEmpty()) {
						name.setForeground(Color.GRAY);
						name.setText(crPlaceholderName);
					}
				}
			});
			
			pass.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
					if (pass.getText().equals(crPlaceholderPass)) {
						pass.setText("");
						pass.setForeground(Color.BLACK);
					}
				}
				@Override
				public void focusLost(FocusEvent arg0) {
					if (pass.getText().isEmpty()) {
						pass.setForeground(Color.GRAY);
						pass.setText(crPlaceholderPass);
					}
				}
			});
			
			panel.setLayout(new GridLayout(2,2));
			panel.add(name);
			panel.add(pass);
			int result = JOptionPane.showConfirmDialog(null, panel, "Create Chat Room", JOptionPane.OK_CANCEL_OPTION);
			
			switch (result) {
			case JOptionPane.OK_OPTION:
				if(name.getText().equals(crPlaceholderName) && pass.getText().equals(crPlaceholderPass)){
					JOptionPane.showMessageDialog(null, "Please enter a Chat Room Name and Password", "Error", JOptionPane.ERROR_MESSAGE);
					actionPerformed(arg0);
				}
				else if(name.getText().equals(crPlaceholderName)){
					JOptionPane.showMessageDialog(null, "Please enter a Chat Room Name", "Error", JOptionPane.ERROR_MESSAGE);
					actionPerformed(arg0);
				}
				
				else if(pass.getText().equals(crPlaceholderPass)){
					JOptionPane.showMessageDialog(null, "Please enter a Chat Room Password", "Error", JOptionPane.ERROR_MESSAGE);
					actionPerformed(arg0);
				}
				else /*enter code for creating chatroom here, also delete the ; after this comment*/;
				break;
			default:
				break;
			}
		}
		
	}
}
