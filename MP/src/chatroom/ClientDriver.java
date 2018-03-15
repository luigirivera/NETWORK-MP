package chatroom;

import client.Client;
import client.ClientGlobalController;
import client.ClientGlobalView;
import client.ClientLoginController;
import client.ClientLoginView;

public class ClientDriver {

	public static void main(String[] args) {
		Client model = new Client();
		ClientLoginView lview = new ClientLoginView(model);
		ClientLoginController lcontroller = new ClientLoginController(model, lview);
		ClientGlobalView gview = new ClientGlobalView(model);
		ClientGlobalController gcontroller = new ClientGlobalController(model, gview);
		
		model.setLoginView(lview);
		model.getChatViews().add(gview);
		lcontroller.init();
		gcontroller.init();
	}

}
