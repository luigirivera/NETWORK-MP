package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.ConcreteMessageFormatter;
import shared.DirectMessage;
import shared.Message;
import shared.MessageFormatter;
import shared.MessageRouter;

public class Client {
	private final static String DEFAULT_SERVER_ADDRESS = "localhost";
	private final static int DEFAULT_SERVER_PORT = 5000;

	private String name;

	private Socket socket;
	private String serverAddress;
	private int serverPort;
	
	private MessageFormatter messageFormatter;
	private MessageRouter messageRouter;

	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;

	private ClientView view;

	public Client() {
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
		this.messageFormatter = new ConcreteMessageFormatter();
		this.messageRouter = new ClientMessageRouter(this);
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

	public void openSocket() throws IOException {
		if (socket != null && !socket.isClosed())
			closeSocket();
		socket = new Socket(serverAddress, serverPort);
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
			this.updateView();
		}
	}

	private void closeStreams() throws IOException {
		inStream.close();
		outStream.close();
	}

	public void readMessage() throws IOException, ClassNotFoundException {
		Message message = (Message) inStream.readObject();
		this.updateView(message);
	}
	
	public void sendMessage(Message message) throws IOException {
		message.setSender(this.name);
		outStream.flush();
		outStream.reset();
		outStream.writeObject(message);
		outStream.flush();
		outStream.reset();
	}
	
	//dm
	public void sendMessage(String text, String destUser) throws IOException {
		DirectMessage message = new DirectMessage();
		message.setContent(text);
		message.setReceiver(destUser);
		this.sendMessage(message);
	}

	//global
	public void sendMessage(String text) throws IOException {
		Message message = new Message();
		message.setContent(text);
		this.sendMessage(message);
	}
	
	public void updateView() {
		view.clearChat();
	}

	public void updateView(Message message) {
		view.appendChat(messageFormatter.format(message));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClientView getView() {
		return view;
	}

	public void setView(ClientView view) {
		this.view = view;
	}

}
