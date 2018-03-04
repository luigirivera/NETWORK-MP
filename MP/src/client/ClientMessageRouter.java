package client;

import java.util.ArrayList;
import java.util.List;

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
			boolean found = false;
			for (ClientObserver obs : client.getObservers()) {
				if (obs instanceof ClientDMView && (((ClientDMView) obs).getDestUser()
						.equalsIgnoreCase(((DirectMessage) message).getSender()) || ((ClientDMView) obs).getDestUser()
						.equalsIgnoreCase(((DirectMessage) message).getRecipient()))) {
					obs.appendChat(message);
					found = true;
					break;
				}
			}
			if(!found) {
				ClientDMView nv = new ClientDMView(client, ((DirectMessage) message).getSender());
				ClientDMController nc = new ClientDMController(client, nv);
				nc.init();
				client.attach(nv);
				nv.appendChat(message);
			}
		}
		
		else if (message instanceof UserListMessage) {
			List<ClientObserver> trash = new ArrayList<ClientObserver>();
			for(ClientObserver obs : client.getObservers()) {
				if(obs instanceof ClientGlobalView) {
					((ClientGlobalView)obs).getUsernameList().clear();
					for(String username : ((UserListMessage)message).getUsernames())
						((ClientGlobalView)obs).getUsernameList().addElement(username);
				}
				if (obs instanceof ClientDMView && !((UserListMessage)message).getUsernames().contains(((ClientDMView)obs).getDestUser()))
					trash.add(obs);
			}
			
			for(ClientObserver obs : trash) {
				client.detach(obs);
				obs.appendChat("User has disconnected");
			}
		}
		
		else if (message instanceof Message) {
			for(ClientObserver obs : client.getObservers()) {
				if(obs instanceof ClientGlobalView)
					obs.appendChat(message);
			}
		}
	}
}
