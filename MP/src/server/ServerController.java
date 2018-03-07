package server;

import java.awt.event.*;
import java.io.IOException;
import java.net.BindException;

import javax.swing.JOptionPane;

public class ServerController {

	private Server model;
	private ServerView view;
	
	public ServerController(Server model, ServerView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		view.addWindowListening(new ServerWindowListener());
		view.addInitiateListener(new InitiateListener());
		view.addTerminateListener(new TerminateListener());
		view.addPortKeyListener(new PortKeyListener());
	}
	
	class ServerWindowListener implements WindowListener{
		@Override
		public void windowClosed(WindowEvent arg0) {
			termination();
			
		}

		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
	}
	
	class InitiateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			initiate();
		}
		
	}
	
	class PortKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				initiate();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	class TerminateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			termination();		
		}
		
	}
	
	private void initiate() {
		int port = 0;
		
		try {
			port = Integer.parseInt(view.getPort().getText());
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter an Integer for the Port", "Error", JOptionPane.ERROR_MESSAGE);
		} 
		
		if(port>=49152 && port<=65535) {
			if(model.init(port)) {
				view.getPort().setEnabled(false);
				view.getInitiate().setVisible(false);
				view.getTerminate().setVisible(true);
			}
		}	
		else
			JOptionPane.showMessageDialog(null, "Please enter a Port Number in the range of 49152-65535", "Error", JOptionPane.ERROR_MESSAGE);
		
	}
	
	private void termination() {
		model.log(String.format("Server at Port: %d is terminated!\n", model.getServer().getLocalPort()));
		try {
			model.getListenerSocket().stop();
			model.getServer().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		view.getPort().setEnabled(true);
		view.getTerminate().setVisible(false);
		view.getInitiate().setVisible(true);
		
	}

}
