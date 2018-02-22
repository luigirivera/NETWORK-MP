package server;

import java.awt.event.*;
import java.io.IOException;

public class ServerController {

	private Server model;
	private ServerView view;
	
	public ServerController(Server model, ServerView view) {
		this.model = model;
		this.view = view;
	}
	
	public void init() {
		view.addWindowListening(new ServerWindowListener());
	}
	
	class ServerWindowListener implements WindowListener{
		@Override
		public void windowClosed(WindowEvent arg0) {
			try {
				model.getServer().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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

}
