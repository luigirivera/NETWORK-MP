package server;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

public class ServerView extends JFrame implements ServerObserver {
	private static final long serialVersionUID = 1L;
	private Server model;
	
	private JPanel panel;
	private JScrollPane laScroll;
	private JTextArea logsArea;
	
	public ServerView(Server model) {
		super("MonoChrome Server Logs");
		this.model = model;
	}
	
	public void init() {
		panel = new JPanel();
		laScroll = new JScrollPane();
		logsArea = new JTextArea();
		
		laScroll.setViewportView(logsArea);
		laScroll.setPreferredSize(new Dimension(375,968));
		logsArea.setEditable(false);
		
		panel.add(laScroll);
		
		add(panel);
		
		setResizable(false);
		setSize(400,1000);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	@Override
	public void update(String message) {
		logsArea.append(message + "\n");
		
	}
	
	public void addWindowListening(WindowListener e) {
		this.addWindowListener(e);
	}
}
