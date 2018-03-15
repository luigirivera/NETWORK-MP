package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import message.LoginResultMessage;
import message.Message;
import message.content.LoginAttempt;
import message.format.MessageFormatter;
import message.format.PlainMessageFormatter;
import message.utility.MessageFactory;
import message.utility.MessageRouter;
import message.utility.MessageScope;

public class Client {
	private final static String DEFAULT_SERVER_ADDRESS = "localhost";
	private final static int DEFAULT_SERVER_PORT = 49162;

	private final String systemOS;
	{
		if (System.getProperty("os.name").startsWith("Windows"))
			systemOS = "Windows";

		else if (System.getProperty("os.name").startsWith("Mac"))
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

	private ClientLoginView loginView;
	private ClientChatRoomListView chatroomListView;

	private ChatViewList chatViews;

	public Client() {
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
		this.messageFormatter = new PlainMessageFormatter();
		this.messageRouter = new ClientMessageRouter(this);

		this.chatViews = new ChatViewList();
	}

	class MessageReceiver implements Runnable {
		private Client client;

		public MessageReceiver(Client client) {
			this.client = client;
		}

		public void run() {
			try {
				while (true) {
					client.messageRouter.route(client.readMessage());
				}
			} catch (Exception e) {
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
	}

	private void initStreams() throws IOException {
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		outStream.flush();
	}

	public boolean attemptLogin(String username) {
		try {
			Message<?> message = MessageFactory.getInstance(new LoginAttempt(username));
			this.sendMessage(message);

			LoginResultMessage response = (LoginResultMessage) this.readMessage();
			switch (response.getContent()) {
			case SUCCESS:
				this.name = username;
				Thread thread = new Thread(new MessageReceiver(this));
				thread.start();
				return true;
			case FAILED:
				loginView.showFailedMessage(response.getContent());
				this.closeSocket();
			default:
				return false;
			}
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
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

	public Message<?> readMessage() throws IOException, ClassNotFoundException {
		return (Message<?>) inStream.readObject();
	}

	public void sendMessage(Message<?> message) throws IOException {
		// setting source now done server-side according to name in UserConnection
		outStream.flush();
		outStream.reset();
		outStream.writeObject(message);
		outStream.flush();
		outStream.reset();
	}

	// dm
	public void sendMessage(String text, String destUser) throws IOException {
		Message<?> message = MessageFactory.getInstance(text);
		message.setDestination(destUser);
		message.setScope(MessageScope.DIRECT);
		this.sendMessage(message);
	}

	// global
	public void sendMessage(String text) throws IOException {
		Message<?> message = MessageFactory.getInstance(text);
		message.setScope(MessageScope.GLOBAL);
		this.sendMessage(message);
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

	public ChatViewList getChatViews() {
		return chatViews;
	}

	public void setChatViews(ChatViewList chatViews) {
		this.chatViews = chatViews;
	}

	public String getSystemOS() {
		return systemOS;
	}
}
