package client.controller;

import java.io.IOException;
import java.util.List;

public interface ClientController {
	public void startListener();
	public boolean isConnected();
	public void openSocket(String address, int port) throws IOException;
	public boolean attemptLogin(String username);
	public void closeSocket() throws IOException;
	public void openLoginWindow();
	public void openGlobalWindow();
	public void openDMWindow(String username);
	public void openGroupWindow(String id);
	public void openRoomWindow(String id, String name);
	public <E> void sendGlobal(E content);
	public <E> void sendDirect(String destination, E content);
	public <E> void sendGroup(String destination, E content);
	public <E> void sendRoom(String destination, E content);
	public void createGroup(List<String> usernames);
	public void createRoom(String name, String password);
	public void joinRoom(String id, String password);
	public void inviteToGroup(String id, String username);
	public void leaveGroup(String id);
}
