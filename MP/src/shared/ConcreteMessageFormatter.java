package shared;

public class ConcreteMessageFormatter implements MessageFormatter {

	@Override
	public String format(Message message) {
		return message.getSender() + " : " + message.getContent();
	}
	
}
