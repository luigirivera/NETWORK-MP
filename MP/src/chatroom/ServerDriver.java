package chatroom;

import java.io.IOException;

import server.*;

public class ServerDriver {

	public static void main(String[] args) {
		Server server = new Server();
		ServerView sView = new ServerView(server);
		ServerController sControl = new ServerController(server, sView);
		
		server.add(sView);
		
		try {
			server.init();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
