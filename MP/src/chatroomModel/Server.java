package chatroomModel;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	private List<Socket> Connections;
	private List<String> Users;
	
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
				
				SocketChecker checker = new SocketChecker(socket, this);
				Thread scThread = new Thread(checker);
				scThread.start();
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
	
	public List<Socket> getConnections() {
		return Connections;
	}

	public void setConnections(List<Socket> connections) {
		Connections = connections;
	}

	public List<String> getUsers() {
		return Users;
	}

	public void setUsers(List<String> users) {
		Users = users;
	}
	

}
