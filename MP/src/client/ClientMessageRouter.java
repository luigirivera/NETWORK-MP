package client;

import shared.DirectMessage;
import shared.Message;
import shared.MessageRouter;

public class ClientMessageRouter implements MessageRouter {
	private Client client;
	
	public ClientMessageRouter(Client client) {
		this.client = client;
	}
	
	@Override
	public void route(Message message) {
		if (message instanceof DirectMessage) {
			
		} else {
			
		}
	}
}
