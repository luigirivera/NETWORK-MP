package chatroom;

import client.*;

public class ClientDriver {

	public static void main(String[] args) {
		Client client = new Client();
		
		ClientGlobalView cGView = new ClientGlobalView(client);
		ClientLoginView cLView = new ClientLoginView(client);
		ClientChatRoomListView cCRLView = new ClientChatRoomListView(client);
	//	new ClientChatRoomView();
	//	new ClientDMView(client, "hello");
	//	new ClientGroupDMView();
		
		ClientGlobalController cController = new ClientGlobalController(client, cGView);
		ClientLoginController cLController = new ClientLoginController(client, cLView);
		ClientChatRoomListController cCRLController = new ClientChatRoomListController(client, cCRLView);
		
		cLController.init();
		cController.init();
		cCRLController.init();
		
		client.init(cGView, cLView, cCRLView);
		
		client.attach(cGView);
		client.attach(cLView);
		client.attach(cCRLView);
	}

}
