package chatroom;

import java.io.IOException;

import chatroomModel.Server;

public class ServerDriver {

	public static void main(String[] args) {
		Server server = new Server();
		
		try {
			server.init();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
