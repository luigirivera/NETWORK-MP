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
	private JTextField port;
	private JLabel portLabel;
	private JButton initiate;
	private JButton terminate;
	
	public ServerView(Server model) {
		super("MonoChrome Server Logs");
		this.model = model;
		
		this.init();
		setLayout(null);
		setResizable(false);
		
		if(model.getSystemOS().equals("Windows"))
			setSize(420,1025);
		else if(model.getSystemOS().equals("Mac"))
			setSize(400,1000);
		else
			setSize(420,1025);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void init() {
		panel = new JPanel();
		laScroll = new JScrollPane();
		logsArea = new JTextArea();
		port = new JTextField("49152");
		portLabel = new JLabel("Port:");
		initiate = new JButton("Initiate");
		terminate = new JButton("Terminate");
		
		laScroll.setViewportView(logsArea);
		logsArea.setEditable(false);
		terminate.setVisible(false);
		logsArea.setLineWrap(true);
		
		panel.setLayout(null);
		panel.add(laScroll);
		panel.add(portLabel);
		panel.add(port);
		panel.add(initiate);
		panel.add(terminate);
		
		add(panel);
		
		panel.setBounds(0, 0, 400, 1000);
		portLabel.setBounds(10, 13, 30, 15);
		port.setBounds(40, 5, 100, 30);
		initiate.setBounds(175, 5, 200, 35);
		terminate.setBounds(175, 5, 200, 35);
		laScroll.setBounds(5, 50, 390, 920);
	}
	

	@Override
	public void update(String message) {
		logsArea.append(message + "\n");
		logsArea.setCaretPosition(logsArea.getDocument().getLength());
	}
	
	public void addWindowListening(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addInitiateListener(ActionListener e) {
		initiate.addActionListener(e);
	}
	
	public void addTerminateListener(ActionListener e) {
		terminate.addActionListener(e);
	}
	
	public void addPortKeyListener(KeyListener e) {
		port.addKeyListener(e);
	}

	public JTextArea getLogsArea() {
		return logsArea;
	}

	public void setLogsArea(JTextArea logsArea) {
		this.logsArea = logsArea;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public JButton getInitiate() {
		return initiate;
	}

	public void setInitiate(JButton initiate) {
		this.initiate = initiate;
	}

	public JButton getTerminate() {
		return terminate;
	}

	public void setTerminate(JButton terminate) {
		this.terminate = terminate;
	}
}
