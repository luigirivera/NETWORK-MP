package chatroomController;

import chatroomModel.Server;
import chatroomView.ServerView;

public class ServerController {

	private Server model;
	private ServerView view;
	
	public ServerController(Server server, ServerView sView) {
		model = server;
		view = sView;
	}

}
