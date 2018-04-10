package server.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.List;

import message.ChatMessage;
import message.GroupInviteMessage;
import message.GroupJoinMessage;
import message.GroupLeaveMessage;
import message.LoginAttemptMessage;
import message.Message;
import message.PrivateGroupCreateMessage;
import message.PublicGroupCreateMessage;
import message.content.ChatRoomInfo;
import message.content.ChatRoomInfoList;
import message.content.LoginResult;
import message.utility.MessageFactory;
import message.utility.MessageScope;
import server.model.ChatGroup;
import server.model.PrivateChatGroup;
import server.model.PublicChatGroup;
import server.model.ServerGroupModel;
import server.model.ServerLogModel;
import server.model.ServerMessageModel;
import server.model.ServerUserModel;
import server.model.User;
import server.model.UserConnection;

public class DefaultServerController implements ServerController {
	private ServerSocket serverSocket;

	private ServerUserModel userModel;
	private ServerMessageModel messageModel;
	private ServerLogModel logModel;
	private ServerGroupModel groupModel;
	private ServerMessageHandler messageHandler;

	public DefaultServerController(ServerUserModel userModel, ServerMessageModel messageModel, ServerLogModel logModel,
			ServerGroupModel groupModel) {
		this.userModel = userModel;
		this.messageModel = messageModel;
		this.logModel = logModel;
		this.groupModel = groupModel;
		this.messageHandler = new ServerMessageHandler();
	}
	
	@Override
	public void startAccepter() {
		Thread thread = new Thread(new UserAccepter());
		thread.start();
	}
	
	class UserAccepter implements Runnable {

		@Override
		public void run() {
			while(serverSocket != null && !serverSocket.isClosed()) {
				try {
					UserConnection uc = new UserConnection (new User(), serverSocket.accept());
					LoginAttemptMessage attempt = (LoginAttemptMessage)uc.readMessage();
					if (userModel.get(attempt.getContent().getUsername())==null) {
						uc.getUser().setName(attempt.getContent().getUsername());
						userModel.add(uc);
						Message<?> result = MessageFactory.getInstance(LoginResult.SUCCESS);
						result.setScope(MessageScope.GLOBAL);
						uc.sendMessage(result);
						logModel.log(uc.getUser().getName() + " connected");
						Message<?> notify = MessageFactory.getInstance(uc.getUser().getName() + " has joined the chat. Say hi!");
						sendGlobal(notify);
						blastUserList();
						blastChatRoomList();
						startListener(uc);
					} else {
						Message<?> result = MessageFactory.getInstance(LoginResult.FAILED);
						uc.sendMessage(result);
					}
				} catch (Exception e) { e.printStackTrace(); }
			}
		}
		
	}

	public void startListener(UserConnection uc) {
		Thread thread = new Thread(new MessageListener(uc));
		thread.start();
	}

	class MessageListener implements Runnable {
		UserConnection uc;
		
		MessageListener(UserConnection uc){
			this.uc = uc;
		}

		/*@Override
		public void run() {
			while(uc.isConnected()) {
				try {
					Message<?> message = uc.readMessage();
					messageHandler.handle(message);
				} catch (Exception e) {
					e.printStackTrace();
					userModel.remove(uc);
					try {
						Message<?> notify = MessageFactory.getInstance(uc.getUser().getName() + " left the chat.");
						sendGlobal(notify);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}*/
		
		public void run() {
			try {
				while(uc.isConnected() && !serverSocket.isClosed()) {
					Message<?> message = uc.readMessage();
					messageHandler.handle(message);
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			groupModel.removeUserFromAll(uc);
			userModel.remove(uc);
			logModel.log(uc.getUser().getName() + " disconnected");
			try {
				uc.closeSocket();
				Message<?> notify = MessageFactory.getInstance(uc.getUser().getName() + " left the chat.");
				sendGlobal(notify);
				blastUserList();
				blastChatRoomList();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void openServer(int port) throws IOException {
		if (this.serverSocket != null && !this.serverSocket.isClosed())
			this.closeServer();
		this.serverSocket = new ServerSocket(port);
		
		logModel.log("Server started!");
		logModel.log("Server IP: " + InetAddress.getLocalHost() + " ; Port: " + serverSocket.getLocalPort());
	}

	@Override
	public void closeServer() throws IOException {
		if (this.serverSocket != null && !this.serverSocket.isClosed()) {
			this.serverSocket.close();
			logModel.log("Server closed!");
		}
	}
	
	class ServerMessageHandler {
		public void handle(Message<?> message) throws IOException {
			if (message instanceof ChatMessage) {
				messageModel.add(message);
				returnToClients(message);
			}
			else if (message instanceof PrivateGroupCreateMessage) {
				//todo
				//blast a "hello! start chatting" message to all members
				PrivateChatGroup group = new PrivateChatGroup();
				UserConnection creator = userModel.get(message.getSource());
				if (creator != null) {
					groupModel.add(group);
					group.add(creator);
					for (String username : ((PrivateGroupCreateMessage)message).getContent().getUsernames()) {
						UserConnection user = userModel.get(username);
						if (user!=null)
							group.add(user);
					}
					blastChatRoomList();
				}
			}
			else if (message instanceof PublicGroupCreateMessage) {
				//todo
				//blast a chatroomlist message updating it with this new room
				PublicGroupCreateMessage pgcm = (PublicGroupCreateMessage)message;
				PublicChatGroup group = new PublicChatGroup(pgcm.getContent().getName(), pgcm.getContent().getPassword());
				UserConnection creator = userModel.get(message.getSource());
				if(creator != null) {
					groupModel.add(group);
					group.add(userModel.get(message.getSource()));
					group.sendInfo();
					blastChatRoomList();
				}
			}
			else if (message instanceof GroupJoinMessage) {
				//todo
				//get the uc associated with source and
				//add to requested group
				GroupJoinMessage gjm = (GroupJoinMessage)message;
				UserConnection user = userModel.get(gjm.getSource());
				ChatGroup group = groupModel.get(gjm.getContent().getId());
				if (user != null && group instanceof PublicChatGroup
						&& ((PublicChatGroup)group).getPassword().equals(gjm.getContent().getPassword())) {
					groupModel.addUserToGroup(user, group);
				} else if (user != null && group instanceof PrivateChatGroup) {
					groupModel.addUserToGroup(user, group);
				}
			}
			else if (message instanceof GroupInviteMessage) {
				GroupInviteMessage gim = (GroupInviteMessage)message;
				UserConnection user = userModel.get(gim.getContent().getUsername());
				ChatGroup group = groupModel.get(gim.getContent().getId());
				if (user != null) {
					groupModel.addUserToGroup(user, group);
				}
			}
			else if (message instanceof GroupLeaveMessage) {
				//todo
				//get the uc associated with source and
				//remove from requested group
				GroupLeaveMessage glm = (GroupLeaveMessage)message;
				UserConnection user = userModel.get(glm.getSource());
				if (user != null) {
					groupModel.removeUserFromGroup(user, groupModel.get(glm.getContent().getId()));
					blastChatRoomList();
				}
			}
		}
		
		private void returnToClients(Message<?> message) throws IOException {
			switch(message.getScope()) {
			case GLOBAL: sendGlobal(message); break;
			case DIRECT: sendDirect(message); break;
			case GROUP: sendGroup(message); break;
			case ROOM: sendRoom(message); break;
			}
		}
	}
	
	@Override
	public void blastUserList() throws IOException {
		Message<?> message = MessageFactory.getInstance(userModel.getNames());
		this.sendGlobal(message);
	}
	
	@Override
	public void blastChatRoomList() throws IOException {
		List<PublicChatGroup> groups = groupModel.getPublic();
		ChatRoomInfoList infos = new ChatRoomInfoList();
		for (PublicChatGroup group : groups) {
			ChatRoomInfo info = new ChatRoomInfo(group.getId(), group.getName());
			infos.add(info);
		}
		Message<?> message = MessageFactory.getInstance(infos);
		this.sendGlobal(message);
	}

	@Override
	public void sendGlobal(Message<?> message) throws IOException{
		message.setScope(MessageScope.GLOBAL);
		message.setTimestamp(LocalDateTime.now());
		for (UserConnection uc : userModel.getAll()) {
			uc.sendMessage(message);
		}
	}

	@Override
	public void sendDirect(Message<?> message) throws IOException{
		message.setScope(MessageScope.DIRECT);
		message.setTimestamp(LocalDateTime.now());
		try {
			userModel.get(message.getDestination()).sendMessage(message);
			userModel.get(message.getSource()).sendMessage(message);
		} catch (NullPointerException e) {e.printStackTrace();}
	}

	@Override
	public void sendGroup(Message<?> message) throws IOException{
		message.setScope(MessageScope.GROUP);
		message.setTimestamp(LocalDateTime.now());
		try {
			groupModel.get(message.getDestination()).sendMessage(message);
		} catch (NullPointerException e) {e.printStackTrace();}
	}
	
	@Override
	public void sendRoom(Message<?> message) throws IOException{
		message.setScope(MessageScope.ROOM);
		message.setTimestamp(LocalDateTime.now());
		try {
			groupModel.get(message.getDestination()).sendMessage(message);
		} catch (NullPointerException e) {e.printStackTrace();}
	}

}
