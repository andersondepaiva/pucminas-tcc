package br.com.andersondepaiva.log.mq.consumer;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.infra.exception.model.ExceptionDto;
import br.com.andersondepaiva.core.mq.Consumer;
import br.com.andersondepaiva.log.mq.consumer.interfaces.IExceptionConsumer;

@Configuration
@Service
public class ExceptionConsumer extends Consumer<ExceptionDto, UUID> implements IExceptionConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionConsumer.class);
	
	@Value("${app.mq.exception-log.queue:exception-event-log-queue}")
	private String queue; 
	
	@Value("${app.mq.exception-log.routing-key:exception.event.*}")
	private String routingKey;
	
	@Value("${app.mq.exception-log.exchange:exception}")
	private String exchange;

	@Bean
	public Exchange exceptionExchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	public Queue exceptionEventLogQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingExceptionConsumer(Queue exceptionEventLogQueue, Exchange exceptionExchange) {
		return BindingBuilder.bind(exceptionEventLogQueue).to(exceptionExchange).with(routingKey).noargs();
	}

	@Override
	@RabbitListener(queues = "#{exceptionEventLogQueue.getName()}")
	public void receive(String json) throws IOException {
		ExceptionDto dto = jsonService.getObjectMapper().readValue(json, ExceptionDto.class);
		Exception ex = dto.getException();
		dto.setException(null);
		LOG.error(jsonService.getObjectMapper().writeValueAsString(dto), ex);
	}

}
