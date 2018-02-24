package chatroom;

import server.Server;
import server.ServerController;
import server.ServerView;

public class ServerDriver {

	public static void main(String[] args) {
		Server server = new Server();
		ServerView sView = new ServerView(server);
		ServerController sControl = new ServerController(server, sView);
		
		
		sView.init();
		server.attach(sView);
		server.init();
		sControl.init();
		
		
	}
}
