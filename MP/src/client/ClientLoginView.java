package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import message.Message;

public class ClientLoginView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	
	private Client model;
	
	private JTextField host;
	private JTextField port;
	private JTextField userName;
	private JButton login;
	private JLabel hostLabel;
	private JLabel portLabel;
	private JPanel panel;
	
	private String usernamePlaceholder = "Username";
	
	public ClientLoginView(Client model) {
		super("MonoChrome - Login");
		
		this.model = model;
		init();
		
		panel.setBounds(0, 0, 500, 200);
		add(panel);
		
		if(model.getSystemOS().equals("Windows"))
			setSize(520, 225);
		else if(model.getSystemOS().equals("Mac"))
			setSize(500, 200);
		else
			setSize(520, 225);
		
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// ------------INITIALIZER------------//
	
	public void init() {
		panel = new JPanel();
		userName = new JTextField();
		host = new JTextField("localhost");
		port = new JTextField("49152");
		hostLabel = new JLabel("Host:");
		portLabel = new JLabel("Port:");
		login = new JButton("Login");
		
		userName.setPreferredSize(new Dimension(100, 25));
		host.setPreferredSize(new Dimension(100, 25));
		port.setPreferredSize(new Dimension(100, 25));
		
		userName.setText("Username");
		userName.setForeground(Color.GRAY);
		
		panel.setLayout(null);	
		panel.add(hostLabel);
		panel.add(host);
		panel.add(portLabel);
		panel.add(port);
		panel.add(userName);
		panel.add(login);
		
		hostLabel.setBounds(60,110,50,15);
		host.setBounds(100, 105, 120,25);
		portLabel.setBounds(60,150,50,15);
		port.setBounds(100, 145, 120, 25);
		userName.setBounds(240, 110, 190, 25);
		login.setBounds(240, 140, 190, 30);
	}
	
	// ------------LISTENERS------------//
	
	public void addLoginBoxListener(KeyListener e, FocusListener f) {
		userName.addKeyListener(e);
		host.addKeyListener(e);
		port.addKeyListener(e);
		userName.addFocusListener(f);
	}

	public void addLoginListener(ActionListener e) {
		login.addActionListener(e);
	}
	
	// ------------UPDATE METHODS------------//

	@Override
	public void appendChat(Message<?> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appendChat(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearChat() {
		// TODO Auto-generated method stub
		
	}
	
	// ------------GETTERS AND SETTERS------------//
	
	public JTextField getHost() {
		return host;
	}

	public void setHost(JTextField host) {
		this.host = host;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}
	
	public JButton getLogin() {
		return login;
	}

	public void setLogin(JButton login) {
		this.login = login;
	}
	
	public JTextField getUserName() {
		return userName;
	}

	public void setUserName(JTextField userName) {
		this.userName = userName;
	}
}
