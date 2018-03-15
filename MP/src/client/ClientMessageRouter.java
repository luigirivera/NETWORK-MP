package client;

import message.FileMessage;
import message.Message;
import message.TextMessage;
import message.UsernameListMessage;
import message.content.UsernameList;
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
		}*/
		
		if(message instanceof TextMessage) {
			ChatView cv = client.getChatViews().getAssociatedView(message);
			if (cv!=null)
				cv.appendChat(message);
			else {
				this.openNewView(message);
			}
		}
		else if (message instanceof FileMessage) {
			ChatView cv = client.getChatViews().getAssociatedView(message);
			if (cv!=null)
				cv.appendChat(message);
			else {
				this.openNewView(message);
			}
		}
		else if (message instanceof UsernameListMessage) {
			ChatView temp = client.getChatViews().getAssociatedView(message);
			if (temp instanceof ShowsUsernameList) {
				((ShowsUsernameList) temp).updateUsernameList((UsernameList)message.getContent());
			}
		}
	}
	
	private void openNewView(Message<?> message) {
		ChatView cv;
		switch (message.getScope()) {
		case DIRECT:
			cv = new ClientDMView(client, message.getSource());
			ClientDMController newController = new ClientDMController(client, (ClientDMView)cv);
			client.getChatViews().add(cv);
			newController.init();
			cv.appendChat(message);
			break;
		case GROUP:
			cv = new ClientGroupDMView(client, message.getSource());
			ClientGroupDMController newGroupController = new ClientGroupDMController(client, (ClientGroupDMView)cv);
			client.getChatViews().add(cv);
			newGroupController.init();
			cv.appendChat(message);
			break;
		case ROOM:
			cv = new ClientGroupDMView(client, message.getSource());
			ClientChatRoomController newRoomController = new ClientChatRoomController(client, (ClientChatRoomView)cv);
			client.getChatViews().add(cv);
			newRoomController.init();
			cv.appendChat(message);
			break;
		default: break;
		}
	}
}
