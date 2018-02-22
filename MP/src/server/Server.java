package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private ServerSocket server;
	private List<UserConnection> connections;
	private List<ServerObserver> observers;

	public final static int PORT = 5000;

	public Server() {
		connections = new ArrayList<UserConnection>();
		observers = new ArrayList<ServerObserver>();

	}

	public void init() throws IOException {
		try {
			server = new ServerSocket(PORT);

			this.log("Server started!");

			while (true) {
				Socket socket = server.accept();

				this.log("Client connected from:" + socket.getLocalAddress().getHostName());

				ConnectionChecker checker = new ConnectionChecker(this.addUser(socket), this);
				Thread ccThread = new Thread(checker);
				ccThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.close();
		}

	}

	private UserConnection addUser(Socket socket) throws IOException {
		Scanner get = new Scanner(socket.getInputStream());
		String name = get.nextLine();
		// do unique name checking later...
		UserConnection uc = new UserConnection(new User(name), socket);
		connections.add(uc);

		/*
		 * for (UserConnection connection : connections) { Socket socket =
		 * connection.getSocket(); PrintWriter print = new
		 * PrintWriter(socket.getOutputStream()); print.println("#?!" + Users);
		 * print.flush(); }
		 */

		get.close();
		return uc;
	}
	
	public void blastMessage(String message) {
		for (UserConnection connection : connections) {
			try {
				Socket socket = connection.getSocket();
				PrintWriter print = new PrintWriter(socket.getOutputStream());
				print.println(message);
				print.flush();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}

	public void attach(ServerObserver obs) {
		observers.add(obs);
	}

	// equivalent of updateAll()
	public void log(String message) {
		System.out.println(message);
		for (ServerObserver ob : observers)
			ob.update(message);
	}

	public ServerSocket getServer() {
		return server;
	}

	public List<UserConnection> getConnections() {
		return connections;
	}

	public List<ServerObserver> getObservers() {
		return observers;
	}

}

class UserConnection {
	private User user;
	private Socket socket;

	public UserConnection(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}