package chatroomView;

import javax.swing.*;

import chatroomModel.Server;

public class ServerView extends JFrame {
	
	private Server model;
	
	public ServerView(Server server) {
		super("Server Logs");
		model = server;
		
		this.init();
		setSize(400,1000);
		setVisible(true);
	}
	
	private void init() {
		JPanel panel = new JPanel();
		JScrollPane laScroll = new JScrollPane();
		JTextArea logsArea = new JTextArea();
		
		laScroll.setViewportView(logsArea);
		logsArea.setSize(500, 519);
		logsArea.setRows(59);
		logsArea.setColumns(33);
		panel.add(laScroll);
		
		add(panel);
	}
}
