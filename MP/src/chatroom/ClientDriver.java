package chatroom;

import client.Client;
import client.ClientLoginController;
import client.ClientLoginView;

public class ClientDriver {

	public static void main(String[] args) {
		Client model = new Client();
		ClientLoginView view = new ClientLoginView(model);
		ClientLoginController controller = new ClientLoginController(model, view);
		
		model.setLoginView(view);
		controller.init();
	}

}
