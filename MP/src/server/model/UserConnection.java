package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.Message;

public class UserConnection {
	public static final int REACH_TIMEOUT_MS = 500;
	
	private User user;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public UserConnection(User user, Socket socket) {
		this.user = user;
		this.socket = socket;
		this.initStreams();
	}

	private void initStreams() {
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		try {
			return this.socket.getInetAddress().isReachable(REACH_TIMEOUT_MS);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Message<?> readMessage() throws IOException, ClassNotFoundException{
		Message<?> msg = (Message<?>)this.in.readObject();
		msg.setSource(user.getName());
		return msg;
	}
	
	public void sendMessage(Message<?> message) throws IOException{
		this.out.writeObject(message);
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

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

}
