package chatroom;

import server.controller.DefaultServerController;
import server.controller.ServerController;
import server.model.DefaultServerGroupData;
import server.model.DefaultServerGroupModel;
import server.model.DefaultServerLogModel;
import server.model.DefaultServerMessageData;
import server.model.DefaultServerMessageModel;
import server.model.DefaultServerUserData;
import server.model.DefaultServerUserModel;
import server.model.ServerGroupModel;
import server.model.ServerLogModel;
import server.model.ServerMessageModel;
import server.model.ServerUserModel;
import server.view.ServerView;

public class ServerDriver {
	public static void main(String[] args) {
		ServerUserModel userModel = new DefaultServerUserModel(new DefaultServerUserData());
		ServerMessageModel messageModel = new DefaultServerMessageModel(new DefaultServerMessageData());
		ServerLogModel logModel = new DefaultServerLogModel();
		ServerGroupModel groupModel = new DefaultServerGroupModel(new DefaultServerGroupData());
		ServerController controller = new DefaultServerController(userModel, messageModel, logModel, groupModel);
		ServerView view = new ServerView(messageModel, logModel, controller);
		messageModel.attach(view);
		logModel.attach(view);
	}
}
