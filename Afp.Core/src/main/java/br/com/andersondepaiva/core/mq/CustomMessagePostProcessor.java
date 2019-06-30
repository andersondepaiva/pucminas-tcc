package br.com.andersondepaiva.core.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class CustomMessagePostProcessor implements MessagePostProcessor {
	
	private final Integer ttl;
	
	public CustomMessagePostProcessor(final Integer ttl) {
        this.ttl = ttl;
    }

	@Override
	public Message postProcessMessage(Message message) throws AmqpException {
		message.getMessageProperties().getHeaders().put("x-message-ttl", ttl.toString());
        return message;
	}

}
