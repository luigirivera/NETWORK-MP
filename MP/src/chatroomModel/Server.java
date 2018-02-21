package chatroomModel;

import java.io.*;
import java.net.*;
import java.util.*;

import chatroomView.ServerObserver;

public class Server {
	
	private List<Socket> connections;
	private List<String> users;
	private ServerSocket server;
	private List<ServerObserver> observers;
	
	public final static int PORT = 5000;
	
	public Server() {
		connections = new ArrayList<Socket>();
		users = new ArrayList<String>();
		observers = new ArrayList<ServerObserver>();
		
	}
	
	public void init() throws IOException {
		try {
			server = new ServerSocket(PORT);
			
			for(ServerObserver ob : observers)
				ob.update("Server started!");
			
			while(true)
			{
				Socket socket = server.accept();
				connections.add(socket);
				
				for(ServerObserver ob : observers)
					ob.update("Client connected from:" + socket.getLocalAddress().getHostName());
				
				this.addUser(socket);
				
				SocketChecker checker = new SocketChecker(socket, this);
				Thread scThread = new Thread(checker);
				scThread.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			server.close();
		}
			
	}
	
	private void addUser(Socket socket) throws IOException {
		Scanner get = new Scanner(socket.getInputStream());
		String name = get.nextLine();
		users.add(name);
		
		for(Socket connection : connections) {
			PrintWriter print = new PrintWriter(connection.getOutputStream());
			print.println("#?!" + users);
			print.flush();
		}
		
		get.close();
	}
	
	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public List<ServerObserver> getObservers() {
		return observers;
	}

	public void setObservers(List<ServerObserver> observers) {
		this.observers = observers;
	}

	public void add(ServerObserver obs) {
		observers.add(obs);
	}
	
	public List<Socket> getConnections() {
		return connections;
	}

	public void setConnections(List<Socket> connections) {
		this.connections = connections;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	

}
