package br.com.andersondepaiva.core.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.core.infra.utils.JsonService;

@Configuration
@Service
public abstract class Consumer<TDto extends BaseDto, PK> implements IConsumer<TDto, PK> {

	@Autowired
	protected JsonService jsonService;
	
	protected Queue buildQueue(String nameQueue) {
		return QueueBuilder
				.durable(nameQueue)
				.withArgument("x-message-ttl", 1000 * 60 * 60).build();
	}
}
