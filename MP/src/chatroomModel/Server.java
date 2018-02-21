package chatroomModel;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	public static List<Socket> Connections;
	public static List<String> Users;
	public final static int PORT = 5000;
	
	public Server() {
		Connections = new ArrayList<Socket>();
		Users = new ArrayList<String>();
		
	}
	
	public void init() throws IOException {
		try {
			ServerSocket server = new ServerSocket(PORT);
			System.out.println("Server started");
			
			while(true)
			{
				Socket socket = server.accept();
				Connections.add(socket);
				
				System.out.println("Client connected from:" + socket.getLocalAddress().getHostName());
				this.addUser(socket);
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	private void addUser(Socket socket) throws IOException {
		Scanner get = new Scanner(socket.getInputStream());
		String name = get.nextLine();
		Users.add(name);
		
		for(Socket connection : Connections) {
			PrintWriter print = new PrintWriter(connection.getOutputStream());
			print.println("#?!" + Users);
			print.flush();
		}
		
		get.close();
	}
	
	

}
