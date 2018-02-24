package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.ConcreteMessageFormatter;
import shared.DirectMessage;
import shared.Message;
import shared.MessageFormatter;
import shared.MessageRouter;
import shared.UserListMessage;

public class Server {
	private ServerSocket serverSocket;
	private UserConnectionList connections;
	private List<ServerObserver> observers;

	private MessageFormatter messageFormatter;
	private MessageRouter messageRouter;

	private final static int PORT = 5000;

	public Server() {
		connections = new UserConnectionList();
		observers = new ArrayList<ServerObserver>();
		messageFormatter = new ConcreteMessageFormatter();
		messageRouter = new ServerMessageRouter(this);
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
					messageRouter.route(message);
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

	private void addUser(Socket socket) throws IOException, ClassNotFoundException {
		UserConnection uc = new UserConnection(new User(), socket);
		Message initMessage = (Message) uc.getInStream().readObject();
        if(isUsernameUnique(initMessage.getSender())) {
    			uc.getUser().setName(initMessage.getSender());
            connections.add(uc);
            this.blastMessage(String.format("%s joined the chat. Say hi!", uc.getUser().getName()));
            this.log(uc.getUser().getName() + " connected from: " + socket.getRemoteSocketAddress());
            this.blastUserList();
            Thread thread = new Thread(new ConnectionMaintainer(this, uc));
            thread.start();
        }
        else {
            System.out.println("Username already taken!");
            Message msg = new Message();
            msg.setSender("Server");
            msg.setContent("Username already in use");
            uc.getOutStream().writeObject(msg);
            socket.close();
        }                  
	}
	
	private boolean isUsernameUnique(String username) {
        for(UserConnection x: connections) {
            if(x.getUser().getName().equalsIgnoreCase(username)) {
                   return false;
            }
        }
        return true;
	}

	public void closeConnection(UserConnection connection) throws IOException {
		connection.getSocket().close();
		connections.remove(connection);
		this.log(connection.getUser().getName() + " disconnected");
		this.blastMessage(connection.getUser().getName() + " disconnected");
		this.blastUserList();
	}
	
	public void blastUserList() {
		List<String> usernames = connections.getUsernames();
		UserListMessage message = new UserListMessage();
		message.setUsernames(usernames);
		this.blastMessage(message);
	}

	public void blastMessage(Message message) {
		for (UserConnection connection : connections) {
			this.sendMessage(message, connection);
		}
	}

	public void blastMessage(String content) {
		Message message = new Message();
		message.setSender("Server");
		message.setContent(content);
		this.blastMessage(message);
	}

	public void sendMessage(Message message, UserConnection dest) {
		try {
			dest.getOutStream().flush();
			dest.getOutStream().reset();
			dest.getOutStream().writeObject(message);
			dest.getOutStream().flush();
			dest.getOutStream().reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String content, UserConnection dest) {
		Message message = new Message();
		message.setSender("Server");
		message.setContent(content);
		this.sendMessage(message, dest);
	}

	public void attach(ServerObserver obs) {
		observers.add(obs);
	}

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

	public UserConnectionList getConnections() {
		return connections;
	}

	public List<ServerObserver> getObservers() {
		return observers;
	}

}

class UserConnectionList extends ArrayList<UserConnection>{
	private static final long serialVersionUID = 1L;
	
	public UserConnection get(String name) {
		for(UserConnection connection : this) {
			if(connection.getUser().getName().equalsIgnoreCase(name))
				return connection;
		}
		return null;
	}
	
	public boolean contains(String name) {
		return this.get(name) != null;
	}
	
	public List<String> getUsernames() {
		List<String> usernames = new ArrayList<String>();
		for(UserConnection connection : this) {
			usernames.add(connection.getUser().getName());
		}
		return usernames;
	}

}

class LimitedUserConnectionHolder extends UserConnectionList {
	private static final long serialVersionUID = 1L;
	// may use this eventually if we want to impose a limit on no. of users
	// add an attribute maxSize that is passed in through constructor
	// override add method to throw an exception if the size is at the
	// limit already
}