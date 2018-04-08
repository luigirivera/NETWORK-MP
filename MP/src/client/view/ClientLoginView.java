package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.controller.ClientController;
import client.model.ClientModel;

public class ClientLoginView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;

	private ClientModel model;
	private ClientController controller;

	private JTextField host;
	private JTextField port;
	private JTextField userName;
	private JButton login;
	private JLabel hostLabel;
	private JLabel portLabel;
	private JPanel panel;

	private String usernamePlaceholder = "Username";

	public ClientLoginView(ClientModel model, ClientController controller) {
		super("MonoChrome - Login");

		this.model = model;
		this.controller = controller;
		init();

		panel.setBounds(0, 0, 500, 200);
		add(panel);

		/*
		 * if (model.getSystemOS().equals("Windows")) setSize(520, 225); else if
		 * (model.getSystemOS().equals("Mac")) setSize(500, 200); else setSize(520,
		 * 225);
		 */
		
		setSize(500, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		setLayout(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initListeners();
	}

	// ------------INITIALIZER------------//

	public void init() {
		panel = new JPanel();
		userName = new JTextField("");
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

		hostLabel.setBounds(60, 110, 50, 15);
		host.setBounds(100, 105, 120, 25);
		portLabel.setBounds(60, 150, 50, 15);
		port.setBounds(100, 145, 120, 25);
		userName.setBounds(240, 110, 190, 25);
		login.setBounds(240, 140, 190, 30);
	}

	private void initListeners() {
		this.addUserNameFocusListener(new UserNameFocusListener());
		this.addLoginListener(new LoginListener());
	}

	// ------------LISTENER ADDERS------------//
	
	public void addUserNameFocusListener(FocusListener e) {
		userName.addFocusListener(e);
	}

	public void addLoginListener(ActionListener e) {
		login.addActionListener(e);
	}
	
	// ------------LISTENER CLASSES------------//
	
	class UserNameFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (userName.getText().equals(usernamePlaceholder) && userName.getForeground()==Color.GRAY) {
				userName.setText("");
				userName.setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (userName.getText().trim().isEmpty()) {
				userName.setText(usernamePlaceholder);
				userName.setForeground(Color.GRAY);
			}
		}
		
	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(userName.getText().trim().isEmpty() || (userName.getText().equals(usernamePlaceholder) && userName.getForeground()==Color.GRAY)) {
				showFailedMessage("Please enter username");
				return;
			} else if (host.getText().trim().isEmpty()) {
				showFailedMessage("Please enter host address");
				return;
			} else if (port.getText().trim().isEmpty()) {
				showFailedMessage("Please enter port");
				return;
			}
			
			int intPort = 0;
			try {
				intPort = Integer.valueOf(port.getText().trim());
			} catch (NumberFormatException ex) {
				showFailedMessage("Please enter valid port number");
				return;
			}
			
			try {
				controller.openSocket(host.getText().trim(), intPort);
				if (	!controller.attemptLogin(userName.getText().trim()))
					showFailedMessage("Username taken");
			} catch (IOException ex) {
				ex.printStackTrace();
				showFailedMessage("Please try again");
			}
		}

	}

	// ------------UPDATE METHODS------------//

	@Override
	public void update() {
		/*if (model.getUsername() == null) {
			showFailedMessage("Please try again");
		} else {
			model.detach(this);
			this.setEnabled(false);
			this.setVisible(false);
		}*/
		if (controller.isConnected()) {
			/*this.setEnabled(false);
			this.setVisible(false);*/
			model.detach(this);
			this.setVisible(false);
			this.dispose();
		}
	}

	private void showFailedMessage(String details) {
		ClientLoginView view = this;
		Thread thread = new Thread() {
			public void run() {
				JOptionPane.showMessageDialog(view, details, "Login failed", JOptionPane.WARNING_MESSAGE);
			}
		};
		thread.start();
	}
}
