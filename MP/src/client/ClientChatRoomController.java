package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.ClientGlobalController.GroupMessageMenuListener;
import client.ClientGlobalController.MessageMenuListener;

public class ClientChatRoomController {
	private Client model;
	private ClientChatRoomView view;
	
	public ClientChatRoomController(Client model , ClientChatRoomView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		view.addSendFileListener(new SendFileListener());
		view.addMessageBoxListener(new MessageBoxKeyListener(), new MessageBoxFocusListener());
		view.addSendMessageListener(new SendMessageListener());
		this.view.addMessageMenuListener(new MessageMenuListener());
		this.view.addGroupMessageMenuListener(new GroupMessageMenuListener());
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
	
	class MessageBoxFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent arg0) {
			if (view.getMessage().getText().equals(ClientChatRoomView.getMessageplaceholdername())) {
				view.getMessage().setText("");
				view.getMessage().setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (view.getMessage().getText().isEmpty()) {
				view.getMessage().setForeground(Color.GRAY);
				view.getMessage().setText(ClientChatRoomView.getMessageplaceholdername());
			}

		}
	}
	
	class MessageBoxKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessage();
				view.getMessage().setForeground(Color.BLACK);
			}
			else {
				JScrollBar h = view.getMessageScroll().getHorizontalScrollBar();
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
	
	class SendMessageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
			view.getMessage().setText(ClientChatRoomView.getMessageplaceholdername());
		}
	}
	
	private void sendMessage() {
		if (!view.getMessage().getText().isEmpty()
				&& !view.getMessage().getText().equals(ClientChatRoomView.getMessageplaceholdername())) {
			try {
				model.sendMessage(view.getMessage().getText(), view.getDestination());
			} catch (IOException e) { e.printStackTrace(); }
			view.getMessage().setText("");
			view.getMessage().setForeground(Color.GRAY);
		}
	}
	
	class UserListListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e))
				if(!view.getMembers().getSelectedValue().equalsIgnoreCase(model.getName()) && view.getMembers().getSelectedIndices().length == 1
				|| view.getMembers().getSelectedValuesList().contains(model.getName()) && view.getMembers().getSelectedIndices().length == 2)
				{
					view.getCreateGroupDM().setEnabled(false);
					view.getPrivateDM().setEnabled(true);
					view.getDmMenu().show(view.getMembers(), e.getX(), e.getY());
				}
			
				else if(view.getMembers().getSelectedValue().equalsIgnoreCase(model.getName()) && view.getMembers().getSelectedIndices().length == 1);
			
				else {
					view.getCreateGroupDM().setEnabled(true);
					view.getPrivateDM().setEnabled(false);
					view.getDmMenu().show(view.getMembers(), e.getX(), e.getY());
				}
			else if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && !view.getMembers().getSelectedValue().equalsIgnoreCase(model.getName())) {
				ClientDMView newView = new ClientDMView(model, view.getMembers().getSelectedValue());
				ClientDMController newController = new ClientDMController(model, newView);
				newController.init();
				model.getChatViews().add(newView);
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
	
	class MessageMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ClientDMView newView = new ClientDMView(model, view.getMembers().getSelectedValue());
			ClientDMController newController = new ClientDMController(model, newView);
			newController.init();
			model.getChatViews().add(newView);
			
		}
		
	}
	
	class GroupMessageMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//dm view stuff
		}
		
	}
}