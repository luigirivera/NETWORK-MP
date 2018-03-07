package message.utility;

import java.io.File;

import message.FileMessage;
import message.FileRequestMessage;
import message.LoginAttemptMessage;
import message.LoginResultMessage;
import message.Message;
import message.TextMessage;
import message.UsernameListMessage;
import message.content.FileRequest;
import message.content.LoginAttempt;
import message.content.LoginResult;
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
		} else if (content instanceof FileRequest) {
			FileRequestMessage message = new FileRequestMessage();
			message.setContent((FileRequest) content);
			return message;
		} else if (content instanceof LoginAttempt) {
			LoginAttemptMessage message = new LoginAttemptMessage();
			message.setContent((LoginAttempt) content);
			return message;
		} else if (content instanceof LoginResult) {
			LoginResultMessage message = new LoginResultMessage();
			message.setContent((LoginResult) content);
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