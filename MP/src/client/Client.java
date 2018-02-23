package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private final static String DEFAULT_SERVER_ADDRESS = "127.0.0.1";
	private final static int DEFAULT_SERVER_PORT = 5000;
	
	private Socket socket;
	private String serverAddress;
	private int serverPort;
	
	public Client() {
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
	}
	
	public void openSocket() throws IOException {
		if(socket!=null && !socket.isClosed())
			closeSocket();
		socket = new Socket(serverAddress, serverPort);
	}
	
	public void closeSocket() throws IOException {
		if (socket!=null)
			socket.close();
	}
	
	public void sendMessage(String msg) throws IOException {
		PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
		pw.println(msg);
		pw.close();
	}
	
	public Socket getSocket() {
		return socket;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
