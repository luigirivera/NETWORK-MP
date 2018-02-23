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
	
	public boolean checkConnection() throws IOException {
		if(!connection.getSocket().getInetAddress().isReachable(500))
		{
			connection.getSocket().close();
			server.getConnections().remove(connection);
			server.blastMessage(connection.getUser().getName() + " disconnected");
			return false;
		}
		return true;
	}

	@Override
	public void run() {
		try {
			input = new Scanner(connection.getSocket().getInputStream());
			output = new PrintWriter(connection.getSocket().getOutputStream());
			
			while(checkConnection())
			{
				
				//debug
				System.out.println("Debug");
				
				if(input.hasNext())
				{
					//debug
					System.out.println("Debug2");
					String message = input.nextLine();
					server.blastMessage(connection.getUser().getName() + ": " + message);
					server.log(connection.getUser().getName() + ": " + message);
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
