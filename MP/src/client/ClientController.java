package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {

	private Client model;
	private ClientView view;
	
	public ClientController(Client model, ClientView view) {
		this.model = model;
		this.view = view;
		
		view.addActionController(new ActionController());
	}
	
	class ActionController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Login"))
				view.getLogin().setEnabled(false);
				
			
		}
		
	}

}
