package chatroom;

import java.io.IOException;

import server.*;

public class ServerDriver {

	public static void main(String[] args) {
		Server server = new Server();
		//can we move this try-catch out of the driver
		//and back into the Server class? idk how though
		//also thread it
		//otherwise it gets stuck on the infinite loop
		/*try {
			server.init();
		}catch(IOException e) {
			e.printStackTrace();
		}*/
		
		ServerView sView = new ServerView(server);
		sView.init();
		server.attach(sView);
		ServerController sControl = new ServerController(server, sView);
		sControl.init();
		
		try {
			server.init();
		}catch(IOException e) {}
	}
}
