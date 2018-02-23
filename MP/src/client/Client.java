package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Message;

public class Client {
	private final static String DEFAULT_SERVER_ADDRESS = "localhost";
	private final static int DEFAULT_SERVER_PORT = 5000;

	private String name;

	private Socket socket;
	private String serverAddress;
	private int serverPort;

	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;

	public Client() {
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
	}

	public void openSocket() throws IOException {
		if (socket != null && !socket.isClosed())
			closeSocket();
		socket = new Socket(serverAddress, serverPort);
		this.initStreams();
	}

	private void initStreams() throws IOException {
		inStream = new ObjectInputStream(socket.getInputStream());
		outStream = new ObjectOutputStream(socket.getOutputStream());
	}

	public void closeSocket() throws IOException {
		if (socket != null) {
			this.closeStreams();
			socket.close();
		}
	}
	
	private void closeStreams() throws IOException {
		inStream.close();
		outStream.close();
	}

	public void sendMessage(String text) throws IOException {
		Message message = new Message();
		message.setSender(this.name);
		message.setContent(text);
		outStream.writeObject(message);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
