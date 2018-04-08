package client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import client.model.ClientModel;
import client.view.ClientDMView;
import client.view.ClientGlobalView;
import client.view.ClientGroupView;
import client.view.ClientLoginView;
import client.view.ClientRoomView;
import message.ChatRoomInfoMessage;
import message.FileMessage;
import message.LoginResultMessage;
import message.Message;
import message.content.GroupInvite;
import message.content.GroupJoin;
import message.content.GroupLeave;
import message.content.LoginAttempt;
import message.content.LoginResult;
import message.content.PrivateGroupCreate;
import message.content.PublicGroupCreate;
import message.utility.MessageFactory;
import message.utility.MessageScope;

public class DefaultClientController implements ClientController {
	public static String DEFAULT_SERVER_ADDRESS = "127.0.0.1";
	public static int DEFAULT_SERVER_PORT = 49152;

	private String serverAddress;
	private int serverPort;

	private ClientModel model;
	private ClientMessageHandler messageHandler;

	private ObjectOutputStream out;
	private ObjectInputStream in;

	private Socket socket;

	public DefaultClientController(ClientModel model) {
		this.model = model;
		this.messageHandler = new ClientMessageHandler();
		this.serverAddress = DEFAULT_SERVER_ADDRESS;
		this.serverPort = DEFAULT_SERVER_PORT;
	}

	@Override
	public void startListener() {
		Thread thread = new Thread(new MessageListener());
		thread.start();
	}

	class MessageListener implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Message<?> message = (Message<?>) in.readObject();
					messageHandler.handle(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (isConnected())
						closeSocket();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	class ClientMessageHandler {
		public void handle(Message<?> message) throws IOException {
			if (message.getScope()==MessageScope.DIRECT) {
				openDMWindow(message.getSource());
			}
			if (message.getScope()==MessageScope.GROUP) {
				openGroupWindow(message.getDestination());
			}
			if (message instanceof ChatRoomInfoMessage && message.getScope()==MessageScope.ROOM) {
				openRoomWindow(((ChatRoomInfoMessage)message).getContent().getId(), ((ChatRoomInfoMessage)message).getContent().getName());
			}
			if (message instanceof FileMessage) {
				File fileA = ((FileMessage)message).getContent();
				File fileB = new File(fileA.getName());
				try {
					FileInputStream fis = new FileInputStream(fileA);
					FileOutputStream fos = new FileOutputStream(fileB);
					while(fis.available()>0) {
						fos.write(fis.read());
					}
					fos.close();
					fis.close();
					((FileMessage)message).setContent(fileB);
				} catch (Exception e) {e.printStackTrace();}
			}
			model.add(message);
		}
	}

	@Override
	public boolean isConnected() {
		return socket!=null && !socket.isClosed();
	}

	@Override
	public void openSocket(String address, int port) throws IOException {
		if (this.socket != null && !this.socket.isClosed())
			this.closeSocket();
		this.serverAddress = address;
		this.serverPort = port;
		this.socket = new Socket(this.serverAddress, this.serverPort);
		this.initStreams();
	}

	private void initStreams() throws IOException {
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out.flush();
	}

	@Override
	public boolean attemptLogin(String username) {
		try {
			if (this.socket != null && !this.socket.isClosed()) {
				LoginAttempt attempt = new LoginAttempt(username);
				this.sendGlobal(attempt);
				LoginResultMessage result = (LoginResultMessage) this.in.readObject();
				if (result.getContent().equals(LoginResult.SUCCESS)) {
					model.setUsername(username);
					model.updateAll();
					this.openGlobalWindow();
					this.startListener();
					return true;
				}
			}
			return false;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void closeSocket() throws IOException {
		if (this.socket != null && !this.socket.isClosed()) {
			this.socket.close();
			model.setUsername(null);
			model.updateAll();
			this.openLoginWindow();
		}
	}

	@Override
	public void openLoginWindow() {
		ClientLoginView view = new ClientLoginView(model, this);
		model.attach(view);
	}

	@Override
	public void openGlobalWindow() {
		ClientGlobalView view = new ClientGlobalView(model, this);
		model.attach(view);
	}

	@Override
	public void openDMWindow(String username) {
		if(!username.equalsIgnoreCase(model.getUsername()) && !model.hasDMObserver(username)) {
			ClientDMView view = new ClientDMView(model, this, username);
			model.attach(view);
		}
	}
	
	@Override
	public void openGroupWindow(String id) {
		if(!model.hasGroupObserver(id)) {
			ClientGroupView view = new ClientGroupView(model, this, id);
			model.attach(view);
		}
	}
	
	@Override
	public void openRoomWindow(String id, String name) {
		if(!model.hasRoomObserver(id)) {
			ClientRoomView view = new ClientRoomView(model, this, id, name);
			model.attach(view);
		}
	}

	private void sendMessage(Message<?> message) {
		try {
			out.flush();
			out.reset();
			out.writeObject(message);
			out.flush();
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <E> void sendGlobal(E content) {
		try {
			Message<?> message = MessageFactory.getInstance(content);
			message.setSource(model.getUsername());
			message.setScope(MessageScope.GLOBAL);

			this.sendMessage(message);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public <E> void sendDirect(String destination, E content) {
		try {
			Message<?> message = MessageFactory.getInstance(content);
			message.setSource(model.getUsername());
			message.setDestination(destination);
			message.setScope(MessageScope.DIRECT);

			this.sendMessage(message);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public <E> void sendGroup(String destination, E content) {
		try {
			Message<?> message = MessageFactory.getInstance(content);
			message.setSource(model.getUsername());
			message.setDestination(destination);
			message.setScope(MessageScope.GROUP);

			this.sendMessage(message);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public <E> void sendRoom(String destination, E content) {
		try {
			Message<?> message = MessageFactory.getInstance(content);
			message.setSource(model.getUsername());
			message.setDestination(destination);
			message.setScope(MessageScope.ROOM);

			this.sendMessage(message);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void createGroup(List<String> usernames) {
		PrivateGroupCreate request = new PrivateGroupCreate(usernames);
		this.sendGlobal(request);
	}

	@Override
	public void createRoom(String name, String password) {
		PublicGroupCreate request = new PublicGroupCreate(name, password);
		this.sendGlobal(request);
	}
	
	@Override
	public void joinRoom(String id, String password) {
		this.sendGlobal(new GroupJoin(id, password));
	}
	
	@Override
	public void inviteToGroup(String id, String username) {
		this.sendGlobal(new GroupInvite(id, username));
	}
	
	@Override
	public void leaveGroup(String id) {
		this.sendGlobal(new GroupLeave(id));
	}

}