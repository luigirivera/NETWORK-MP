package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectionChecker implements Runnable {

	private UserConnection connection;
	private Server server;
	private Scanner input;
	private PrintWriter output;
	
	public ConnectionChecker(UserConnection connection, Server server) {
		this.connection = connection;
		this.server = server;
	}
	
	public void checkConnection() throws IOException {
		if(!connection.getSocket().isConnected())
		{
			connection.getSocket().close();
			server.getConnections().remove(connection);
			server.blastMessage(connection.getUser().getName() + " disconnected");
		}
	}

	@Override
	public void run() {
		try {
			input = new Scanner(connection.getSocket().getInputStream());
			output = new PrintWriter(connection.getSocket().getOutputStream());
			
			while(true)
			{
				checkConnection();
				
				if(input.hasNext())
				{
					String message = input.nextLine();
					
					System.out.println(connection.getSocket().getLocalAddress().getHostName() + " said: " + message);
					
					for(UserConnection connection : server.getConnections()) {
						Socket socket = connection.getSocket();
						PrintWriter out = new PrintWriter(socket.getOutputStream());
						out.println(message);
						out.flush();
						System.out.println("Sent to: " + socket.getLocalAddress().getHostName());
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
