package chatroom;

import client.controller.ClientController;
import client.controller.DefaultClientController;
import client.model.ClientModel;
import client.model.DefaultClientModel;
import client.view.ClientLoginView;

public class ClientDriver {
	public static void main(String[] args) {
		ClientModel cm = new DefaultClientModel();
		ClientController cc = new DefaultClientController(cm);
		ClientLoginView cv = new ClientLoginView(cm, cc);
		cm.attach(cv);
	}
}