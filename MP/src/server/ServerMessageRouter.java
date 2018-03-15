package server;

import message.FileMessage;
import message.FileRequestMessage;
import message.Message;
import message.TextMessage;
import message.utility.MessageRouter;

public class ServerMessageRouter implements MessageRouter {
	private Server server;

	public ServerMessageRouter(Server server) {
		this.server = server;
	}

	@Override
	public void route(Message<?> message) {
		if(message instanceof TextMessage) {
			System.out.println(message.getContent());
			switch(message.getScope()) {
			case GLOBAL: routeGlobal(message); break;
			case DIRECT: routeDirect(message); break;
			case GROUP: routeGroup(message); break;
			default: break;
			}
		}
		else if (message instanceof FileMessage) {
			switch(message.getScope()) {
			case GLOBAL: routeGlobal(message); break;
			case DIRECT: routeDirect(message); break;
			case GROUP: routeGroup(message); break;
			default: break;
			}
		}
		else if (message instanceof FileRequestMessage) {
			//send FileMessage containing requested file back to user
		}
	}
	
	private void routeGlobal(Message<?> message) {
		server.blastMessage(message);
	}
	
	private void routeDirect(Message<?> message) {
		try {
			UserConnection src = server.getConnections().get(message.getSource());
			UserConnection dest = server.getConnections().get(message.getDestination());
			server.sendMessage(message, dest);
			server.sendMessage(message, src);
		} catch (NullPointerException e) { e.printStackTrace(); }
	}
	
	private void routeGroup(Message<?> message) {
		
	}
}
