package client;

import javax.swing.*;

public class ClientView extends JFrame {

	private Client model;
	private JList userList;
	private JTextField userName;
	private JTextField message;
	private JTextArea chat;
	
	
	public ClientView(Client client) {
		super("Datcord");
		
		model = client;
		
		setSize(750,600);
		this.init();
		setVisible(true);
	}
	
	private void init() {
		userList = new JList(); //add the usernames to the JList as a parameter
		userName = new JTextField();
		message = new JTextField();
		chat = new JTextArea();
	}
}
