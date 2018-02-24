package server;

import shared.DirectMessage;
import shared.Message;
import shared.MessageRouter;

public class ServerMessageRouter implements MessageRouter {
	private Server server;

	public ServerMessageRouter(Server server) {
		this.server = server;
	}

	@Override
	public void route(Message message) {
		if(message instanceof DirectMessage) {
			try {
				UserConnection dest = server.getConnections().get(((DirectMessage)message).getRecipient());
				server.sendMessage(message, dest);
			} catch (NullPointerException e) { e.printStackTrace(); }
		} else {
			server.blastMessage(message);
		}
	}
}
