package client;

import shared.DirectMessage;
import shared.Message;
import shared.MessageRouter;
import shared.UserListMessage;

public class ClientMessageRouter implements MessageRouter {
	private Client client;

	public ClientMessageRouter(Client client) {
		this.client = client;
	}

	@Override
	public void route(Message message) {
		if (message instanceof DirectMessage) {
			for (ClientObserver obs : client.getObservers()) {
				if (obs instanceof ClientDMView && ((ClientDMView) obs).getDestUser()
						.equalsIgnoreCase(((DirectMessage) message).getRecipient())) {
					obs.appendChat(message);
					return;
				}
			}
			client.attach(new ClientDMView(client, ((DirectMessage) message).getRecipient()));
		} else if (message instanceof UserListMessage) {
			for(ClientObserver obs : client.getObservers()) {
				if(obs instanceof ClientView) {
					((ClientView)obs).getUsernameList().clear();
					for(String username : ((UserListMessage)message).getUsernames())
						((ClientView)obs).getUsernameList().addElement(username);
				}
			}
		} else {
			for(ClientObserver obs : client.getObservers()) {
				if(obs instanceof ClientView)
					obs.appendChat(message);
			}
		}
	}
}
