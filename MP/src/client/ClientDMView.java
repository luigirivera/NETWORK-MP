package client;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientDMView extends JFrame implements ClientObserver {
	private static final long serialVersionUID = 1L;
	private static final String placeholderName = "Message";

	private Client model;
	private String destUser;

	private JPanel dmPanel;
	private JTextField dmMessage;
	private JButton dmSend;
	private JTextArea dmChat;
	private JScrollPane dmChatScroll;
	private JScrollPane dmMessageScroll;

	public ClientDMView(Client model, String destUser) {
		super(String.format("<%s> - MonoChrome", destUser));

		this.model = model;
		this.destUser = destUser;

		init();
		this.setSize(500, 500);
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
	}

	// ------------INITIALIZER------------//

	public void init() {
		dmPanel = new JPanel();
		dmMessage = new JTextField();
		dmSend = new JButton("Send");
		dmChat = new JTextArea();
		dmChatScroll = new JScrollPane();
		dmMessageScroll = new JScrollPane();

		dmChatScroll.setViewportView(dmChat);
		dmMessageScroll.setViewportView(dmMessage);

		dmChatScroll.setPreferredSize(new Dimension(480, 420));
		dmMessageScroll.setPreferredSize(new Dimension(400, 40));

		dmChat.setEditable(false);

		dmPanel.add(dmChatScroll);
		dmPanel.add(dmMessageScroll);
		dmPanel.add(dmSend);
		this.add(dmPanel);

		dmPanel.setBounds(0, 0, 500, 478);
	}

	// ------------LISTENERS------------//
	public void addDMSendMessageListener(ActionListener e) {
		dmSend.addActionListener(e);
	}

	public void addDMMessageBoxListener(KeyListener e, FocusListener f) {
		dmMessage.addKeyListener(e);
		dmMessage.addFocusListener(f);
	}

	// ------------UPDATE METHODS------------//
	@Override
	public void appendChat(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearChat() {
		// TODO Auto-generated method stub

	}

	// ------------GETTERS AND SETTERS------------//
	public String getDestUser() {
		return destUser;
	}

	public void setDestUser(String destUser) {
		this.destUser = destUser;
	}

	public JPanel getDmPanel() {
		return dmPanel;
	}

	public void setDmPanel(JPanel dmPanel) {
		this.dmPanel = dmPanel;
	}

	public JTextField getDmMessage() {
		return dmMessage;
	}

	public void setDmMessage(JTextField dmMessage) {
		this.dmMessage = dmMessage;
	}

	public JButton getDmSend() {
		return dmSend;
	}

	public void setDmSend(JButton dmSend) {
		this.dmSend = dmSend;
	}

	public JTextArea getDmChat() {
		return dmChat;
	}

	public void setDmChat(JTextArea dmChat) {
		this.dmChat = dmChat;
	}

	public JScrollPane getDmChatScroll() {
		return dmChatScroll;
	}

	public void setDmChatScroll(JScrollPane dmChatScroll) {
		this.dmChatScroll = dmChatScroll;
	}

	public JScrollPane getDmMessageScroll() {
		return dmMessageScroll;
	}

	public void setDmMessageScroll(JScrollPane dmMessageScroll) {
		this.dmMessageScroll = dmMessageScroll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getPlaceholdername() {
		return placeholderName;
	}

}
