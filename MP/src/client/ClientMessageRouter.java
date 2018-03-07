package client;

import message.FileMessage;
import message.Message;
import message.TextMessage;
import message.UsernameListMessage;
import message.utility.MessageRouter;

public class ClientMessageRouter implements MessageRouter {
	private Client client;

	public ClientMessageRouter(Client client) {
		this.client = client;
	}

	@Override
	public void route(Message<?> message) {
		
		
		/*if (message instanceof DirectMessage) {
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
				if(obs instanceof ClientView) {
					((ClientView)obs).getUsernameList().clear();
					for(String username : ((UserListMessage)message).getUsernames())
						((ClientView)obs).getUsernameList().addElement(username);
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
				if(obs instanceof ClientView)
					obs.appendChat(message);
			}
		}*/
		
		if(message instanceof TextMessage) {
			switch(message.getScope()) {
			case GLOBAL: break;
			case DIRECT: break;
			case GROUP: break;
			default: break;
			}
		}
		else if (message instanceof FileMessage) {
			switch(message.getScope()) {
			case GLOBAL: break;
			case DIRECT: break;
			case GROUP: break;
			default: break;
			}
		}
		else if (message instanceof UsernameListMessage) {
			switch(message.getScope()) {
			case GLOBAL: break;
			case GROUP: break;
			default: break;
			}
		}
	}
}
