package chatroom;

import client.*;

public class ClientDriver {

	public static void main(String[] args) {
		Client client = new Client();
		client.init();
		ClientView cView = new ClientView(client);
		cView.init();
		ClientController cController = new ClientController(client, cView);
		cController.init();
	}

}
