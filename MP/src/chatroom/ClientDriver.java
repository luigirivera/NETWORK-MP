package chatroom;

import client.*;

public class ClientDriver {

	public static void main(String[] args) {
		Client client = new Client();
		ClientView cView = new ClientView(client);
		ClientController cController = new ClientController(client, cView);
		client.init();
	}

}
