package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

public class UserConnection {
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

	public Message<?> readMessage() throws IOException, ClassNotFoundException {
		Message<?> message = (Message<?>) this.getInStream().readObject();
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
