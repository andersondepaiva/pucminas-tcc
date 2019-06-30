package br.com.andersondepaiva.core.infra.exception.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.infra.exception.model.ExceptionDto;
import br.com.andersondepaiva.core.infra.exception.producer.interfaces.IExceptionProducer;
import br.com.andersondepaiva.core.mq.Producer;

@Configuration
@Service
public class ExceptionProducer extends Producer<ExceptionDto, UUID> implements IExceptionProducer {

	@Bean
	public Exchange exceptionExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange("exception");
	}

}
