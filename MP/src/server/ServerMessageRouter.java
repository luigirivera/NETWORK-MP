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
				UserConnection src = server.getConnections().get(message.getSender());
				UserConnection dest = server.getConnections().get(((DirectMessage) message).getRecipient());
				server.sendMessage(message, src);
				server.sendMessage(message, dest);
			} catch (NullPointerException e) {}
		} else {
			server.blastMessage(message);
		}
	}
}
