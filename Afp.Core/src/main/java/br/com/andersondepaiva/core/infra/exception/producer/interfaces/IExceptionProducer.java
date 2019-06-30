package br.com.andersondepaiva.core.infra.exception.producer.interfaces;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;

import br.com.andersondepaiva.core.infra.exception.model.ExceptionDto;
import br.com.andersondepaiva.core.mq.IProducer;

public interface IExceptionProducer extends IProducer<ExceptionDto, UUID> {
	
	Exchange exceptionExchange();

}
