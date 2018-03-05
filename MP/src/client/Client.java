package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import shared.ConcreteMessageFormatter;
import shared.DirectMessage;
import shared.Message;
import shared.MessageFormatter;
import shared.MessageRouter;

public class Client {
	private final static String DEFAULT_SERVER_ADDRESS = "localhost";
	private final static int DEFAULT_SERVER_PORT = 49162;
	
	private final String systemOS;
	{
		if(System.getProperty("os.name").startsWith("Windows"))
			systemOS = "Windows";
			
		else if(System.getProperty("os.name").startsWith("Mac"))
			systemOS = "Mac";
			
		else
			systemOS = "Linux";	
	}

	private String name;

	private Socket socket;
	private String serverAddress;
	private int serverPort;

	private MessageFormatter messageFormatter;
	private MessageRouter messageRouter;

	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	
	private ClientGlobalView globalView;
	private ClientLoginView loginView;
	private ClientChatRoomListView chatroomListView;

	private List<ClientObserver> observers;

	public Client() {
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
		this.messageFormatter = new ConcreteMessageFormatter();
		this.messageRouter = new ClientMessageRouter(this);
		this.observers = new ArrayList<ClientObserver>();
	}
	
	public void init(ClientGlobalView GlobalView, ClientLoginView LoginView, ClientChatRoomListView ChatRoomListView) {
		globalView = GlobalView;
		loginView = LoginView;
		chatroomListView = ChatRoomListView;
	}

	class MessageReceiver implements Runnable {
		private Client client;
		
		public MessageReceiver(Client client) {
			this.client = client;
		}

		public void run() {
			try {
				while (true) {
					client.readMessage();
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					client.closeSocket();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void openSocket(String address, int port) throws IOException {
		if (socket != null && !socket.isClosed())
			closeSocket();
		socket = new Socket(address, port);
		serverAddress = address;
		serverPort = port;
		this.initStreams();
		Thread thread = new Thread(new MessageReceiver(this));
		thread.start();

	}

	private void initStreams() throws IOException {
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		outStream.flush();
	}

	public void closeSocket() throws IOException {
		if (socket != null) {
			this.closeStreams();
			socket.close();
			name = null;
		}
	}

	private void closeStreams() throws IOException {
		inStream.close();
		outStream.close();
	}

	public void readMessage() throws IOException, ClassNotFoundException {
		Message message = (Message) inStream.readObject();
		messageRouter.route(message);
	}

	public void sendMessage(Message message) throws IOException {
		message.setSender(this.name);
		outStream.flush();
		outStream.reset();
		outStream.writeObject(message);
		outStream.flush();
		outStream.reset();
	}

	// dm
	public void sendMessage(String text, String destUser) throws IOException {
		DirectMessage message = new DirectMessage();
		message.setContent(text);
		message.setRecipient(destUser);
		this.sendMessage(message);
	}

	// global
	public void sendMessage(String text) throws IOException {
		Message message = new Message();
		message.setContent(text);
		this.sendMessage(message);
	}

	public void attach(ClientObserver obs) {
		this.observers.add(obs);
	}

	public void detach(ClientObserver obs) {
		this.observers.remove(obs);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClientObserver> getObservers() {
		return observers;
	}
	
	public ClientGlobalView getGlobalView() {
		return globalView;
	}

	public void setGlobalView(ClientGlobalView globalView) {
		this.globalView = globalView;
	}

	public ClientLoginView getLoginView() {
		return loginView;
	}

	public void setLoginView(ClientLoginView loginView) {
		this.loginView = loginView;
	}
	
	public ClientChatRoomListView getChatroomListView() {
		return chatroomListView;
	}

	public void setChatroomListView(ClientChatRoomListView chatroomListView) {
		this.chatroomListView = chatroomListView;
	}
	
	public String getSystemOS() {
		return systemOS;
	}
}
