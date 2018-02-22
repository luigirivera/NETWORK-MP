package server;

import java.awt.event.*;

import javax.swing.*;

public class ServerView extends JFrame implements ServerObserver {
	private static final long serialVersionUID = 1L;
	private Server model;
	
	private JPanel panel;
	private JScrollPane laScroll;
	private JTextArea logsArea;
	
	public ServerView(Server model) {
		super("Datcord Server Logs");
		this.model = model;
	}
	
	public void init() {
		panel = new JPanel();
		laScroll = new JScrollPane();
		logsArea = new JTextArea();
		
		laScroll.setViewportView(logsArea);
		logsArea.setSize(500, 519);
		logsArea.setRows(59);
		logsArea.setColumns(33);
		logsArea.setEditable(false);
		
		
		panel.add(laScroll);
		
		add(panel);
		
		setSize(400,1000);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	@Override
	public void update(String message) {
		logsArea.append(message + "\n");
		
	}
	
	public void addWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
}
