package server;

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
	
	public void checkConnection() throws IOException {
		if(!socket.isConnected())
		{
			server.getConnections().remove(socket);
			
			for(Socket connection : server.getConnections()) {
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.println(connection.getLocalAddress().getHostName() + " disconnected");
				out.flush();
				
				for(ServerObserver ob : server.getObservers())
					ob.update(connection.getLocalAddress().getHostName() + " disconnected");
			}
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
					String message = input.nextLine();
					
					System.out.println(socket.getLocalAddress().getHostName() + " said: " + message);
					
					for(Socket connection : server.getConnections()) {
						PrintWriter out = new PrintWriter(socket.getOutputStream());
						out.println(message);
						out.flush();
						for(ServerObserver ob : server.getObservers())
							ob.update("Sent to: " + connection.getLocalAddress().getHostName());
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
