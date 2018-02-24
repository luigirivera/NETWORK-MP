package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.ConcreteMessageFormatter;
import shared.Message;
import shared.MessageFormatter;

public class Server {
	private ServerSocket serverSocket;
	private List<UserConnection> connections;
	private List<ServerObserver> observers;
	
	private MessageFormatter messageFormatter;

	private final static int PORT = 5000;

	public Server() {
		connections = new ArrayList<UserConnection>();
		observers = new ArrayList<ServerObserver>();
		messageFormatter = new ConcreteMessageFormatter();
	}

	public void init() {
		try {
			serverSocket = new ServerSocket(PORT);

			this.log("Server started!");
			this.log("Server IP: " + InetAddress.getLocalHost() + " ; Port: " + serverSocket.getLocalPort());
			Thread slThread = new Thread(new SocketListen(this));
			slThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SocketListen implements Runnable {
		private Server server;

		public SocketListen(Server server) {
			this.server = server;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					this.server.addUser(socket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ConnectionMaintainer implements Runnable {
		private Server server;
		private UserConnection connection;

		public ConnectionMaintainer(Server server, UserConnection connection) {
			this.server = server;
			this.connection = connection;
		}

		public boolean checkConnection() throws IOException {
			if (!connection.getSocket().getInetAddress().isReachable(5000)) {
				System.out.println("Could not reach");
				server.closeConnection(connection);
				return false;
			}
			return true;
		}

		public void run() {
			try {
				while (this.checkConnection()) {
					Message message = connection.readMessage();
					server.blastMessage(message);
				}
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} finally {
				try {
					server.closeConnection(connection);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private UserConnection addUser(Socket socket) throws IOException, ClassNotFoundException {
		// do unique name checking later...
		UserConnection uc = new UserConnection(new User(), socket);
		Message initMessage = (Message) uc.getInStream().readObject();
		uc.getUser().setName(initMessage.getSender());
		connections.add(uc);
		this.log(uc.getUser().getName() + " connected from: " + socket.getRemoteSocketAddress());
		Thread thread = new Thread(new ConnectionMaintainer(this, uc));
		thread.start();
		return uc;
	}

	public void closeConnection(UserConnection connection) throws IOException {
		connection.getSocket().close();
		this.getConnections().remove(connection);
		this.log(connection.getUser().getName() + " disconnected");
		this.blastMessage(connection.getUser().getName() + " disconnected");
	}

	public void blastMessage(Message message) {
		/*
		 * for (UserConnection connection : connections) { try { Socket socket =
		 * connection.getSocket(); PrintWriter print = new
		 * PrintWriter(socket.getOutputStream()); print.println(message); print.flush();
		 * } catch (IOException e) { e.printStackTrace(); } }
		 */
		this.log(message);
		for (UserConnection connection : connections) {
			try {
				connection.getOutStream().flush();
				connection.getOutStream().reset();
				connection.getOutStream().writeObject(message);
				connection.getOutStream().flush();
				connection.getOutStream().reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void blastMessage(String content) {
		Message message = new Message();
		message.setSender("Server");
		message.setContent(content);
		this.blastMessage(message);
	}

	public void attach(ServerObserver obs) {
		observers.add(obs);
	}

	// equivalent of updateAll()
	public void log(Message message) {
		this.log(messageFormatter.format(message));
	}
	
	public void log(String text) {
		System.out.println(text);
		for (ServerObserver ob : observers)
			ob.update(text);
	}

	public ServerSocket getServer() {
		return serverSocket;
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
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;

	public UserConnection(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
		try {
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Message readMessage() throws IOException, ClassNotFoundException {
		Message message = (Message) this.getInStream().readObject();
		return message;
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

	public ObjectInputStream getInStream() {
		return inStream;
	}

	public void setInStream(ObjectInputStream inStream) {
		this.inStream = inStream;
	}

	public ObjectOutputStream getOutStream() {
		return outStream;
	}

	public void setOutStream(ObjectOutputStream outStream) {
		this.outStream = outStream;
	}

}