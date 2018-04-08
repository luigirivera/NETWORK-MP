package message.utility;

import java.io.File;

import message.ChatRoomInfoListMessage;
import message.ChatRoomInfoMessage;
import message.FileMessage;
import message.FileRequestMessage;
import message.GroupInviteMessage;
import message.GroupJoinMessage;
import message.GroupLeaveMessage;
import message.LoginAttemptMessage;
import message.LoginResultMessage;
import message.Message;
import message.PrivateGroupCreateMessage;
import message.PublicGroupCreateMessage;
import message.TextMessage;
import message.UsernameListMessage;
import message.content.ChatRoomInfo;
import message.content.ChatRoomInfoList;
import message.content.FileRequest;
import message.content.GroupInvite;
import message.content.GroupJoin;
import message.content.GroupLeave;
import message.content.LoginAttempt;
import message.content.LoginResult;
import message.content.PrivateGroupCreate;
import message.content.PublicGroupCreate;
import message.content.UsernameList;

public abstract class MessageFactory {
	public static Message<?> getInstance(Object content) throws IllegalArgumentException {
		if (content instanceof String) {
			TextMessage message = new TextMessage();
			message.setContent((String) content);
			return message;
		} else if (content instanceof File) {
			FileMessage message = new FileMessage();
			message.setContent((File) content);
			return message;
		} else if (content instanceof ChatRoomInfo) {
			ChatRoomInfoMessage message = new ChatRoomInfoMessage();
			message.setContent((ChatRoomInfo) content);
			return message;
		} else if (content instanceof ChatRoomInfoList) {
			ChatRoomInfoListMessage message = new ChatRoomInfoListMessage();
			message.setContent((ChatRoomInfoList) content);
			return message;
		} else if (content instanceof FileRequest) {
			FileRequestMessage message = new FileRequestMessage();
			message.setContent((FileRequest) content);
			return message;
		} else if (content instanceof GroupInvite) {
			GroupInviteMessage message = new GroupInviteMessage();
			message.setContent((GroupInvite) content);
			return message;
		} else if (content instanceof GroupJoin) {
			GroupJoinMessage message = new GroupJoinMessage();
			message.setContent((GroupJoin) content);
			return message;
		} else if (content instanceof GroupLeave) {
			GroupLeaveMessage message = new GroupLeaveMessage();
			message.setContent((GroupLeave) content);
			return message;
		} else if (content instanceof LoginAttempt) {
			LoginAttemptMessage message = new LoginAttemptMessage();
			message.setContent((LoginAttempt) content);
			return message;
		} else if (content instanceof LoginResult) {
			LoginResultMessage message = new LoginResultMessage();
			message.setContent((LoginResult) content);
			return message;
		} else if (content instanceof PrivateGroupCreate) {
			PrivateGroupCreateMessage message = new PrivateGroupCreateMessage();
			message.setContent((PrivateGroupCreate) content);
			return message;
		} else if (content instanceof PublicGroupCreate) {
			PublicGroupCreateMessage message = new PublicGroupCreateMessage();
			message.setContent((PublicGroupCreate) content);
			return message;
		} else if (content instanceof UsernameList) {
			UsernameListMessage message = new UsernameListMessage();
			message.setContent((UsernameList) content);
			return message;
		} else
			throw new IllegalArgumentException("No message type holds this content!");
	}

	public static void main(String[] args) {
		String myContent = "Hello world";

		Message<?> message = MessageFactory.getInstance(myContent);
		System.out.println(message.getContent());
		System.out.println(message.getClass().getSimpleName());
	}
}