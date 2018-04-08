package server.controller;

import java.io.IOException;

import message.Message;

public interface ServerController {
	public void startAccepter();

	public void openServer(int port) throws IOException;

	public void closeServer() throws IOException;
	
	public void blastUserList() throws IOException;
	
	public void blastChatRoomList() throws IOException;

	public void sendGlobal(Message<?> message) throws IOException;

	public void sendDirect(Message<?> message) throws IOException;

	public void sendGroup(Message<?> message) throws IOException;
	
	public void sendRoom(Message<?> message) throws IOException;
}
