package chatroomModel;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketChecker implements Runnable {

	private Socket socket;
	private Server server;
	private Scanner input;
	private PrintWriter output;
	
	public SocketChecker(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}
	
	public void checkConnection() {
		if(!socket.isConnected())
		{
			server.getConnections().remove(socket);
			
			
		}
	}

	@Override
	public void run() {
		try {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream());
			
			while(true)
			{
				checkConnection();
				
				if(input.hasNext())
				{
					
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}
