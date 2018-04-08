package server.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import message.Message;
import message.format.MessageFormatter;
import message.format.PlainMessageFormatter;
import server.controller.ServerController;
import server.model.ServerLogModel;
import server.model.ServerMessageModel;

public class ServerView extends JFrame implements ServerMessageObserver, ServerLogObserver {
	private static final long serialVersionUID = 1L;
	private ServerMessageModel messageModel;
	private ServerLogModel logModel;
	private ServerController controller;
	
	private MessageFormatter messageFormatter;
	
	private JPanel panel;
	private JScrollPane laScroll;
	private JTextArea logsArea;
	private JTextField port;
	private JLabel portLabel;
	private JButton initiate;
	private JButton terminate;
	
	public ServerView(ServerMessageModel messageModel, ServerLogModel logModel, ServerController controller) {
		super("MonoChrome Server Logs");
		this.messageModel = messageModel;
		this.logModel = logModel;
		this.controller = controller;
		
		this.messageFormatter = new PlainMessageFormatter();
		
		this.init();
		setLayout(null);
		setResizable(false);
		
		/*if(model.getSystemOS().equals("Windows"))
			setSize(420,1025);
		else if(model.getSystemOS().equals("Mac"))
			setSize(400,1000);
		else
			setSize(420,1025);*/
		setSize(400,1000);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initListeners();
		
	}
	
	private void init() {
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
	
	private void initListeners() {
		addViewWindowListener(new ViewWindowListener());
		addInitiateListener(new InitiateListener());
		addTerminateListener(new TerminateListener());
	}
	
	//LISTENER ADDERS
	public void addViewWindowListener(WindowListener e) {
		this.addWindowListener(e);
	}
	
	public void addInitiateListener(ActionListener e) {
		initiate.addActionListener(e);
	}
	
	public void addTerminateListener(ActionListener e) {
		terminate.addActionListener(e);
	}
	
	//LISTENER CLASSES
	class ViewWindowListener extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			try {
				controller.closeServer();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	class InitiateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			initiate.setEnabled(false);
			try {
				controller.openServer(Integer.valueOf(port.getText().trim()));
				controller.startAccepter();
				
				initiate.setVisible(false);
				terminate.setVisible(true);
				terminate.setEnabled(true);
			} catch (NumberFormatException | IOException ex) {
				ex.printStackTrace();
				initiate.setEnabled(true);
			}
		}
		
		
	}
	
	class TerminateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			terminate.setEnabled(false);
			try {
				controller.closeServer();
				
				terminate.setVisible(false);
				initiate.setVisible(true);
				initiate.setEnabled(true);
			} catch (IOException ex) {
				ex.printStackTrace();
				terminate.setEnabled(true);
			}
		}
		
	}
	
	//UPDATE METHODS
	@Override
	public void updateMessages() {
		for (Message<?> message : messageModel.getAll()) {
			logsArea.append(messageFormatter.format(message) + "\n");
			logsArea.setCaretPosition(logsArea.getDocument().getLength());
		}
	}
	
	@Override
	public void updateLog() {
		logsArea.append(logModel.getLast() + "\n");
		logsArea.setCaretPosition(logsArea.getDocument().getLength());
	}
}
