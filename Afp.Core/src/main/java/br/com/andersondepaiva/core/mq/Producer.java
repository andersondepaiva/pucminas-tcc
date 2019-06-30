package br.com.andersondepaiva.core.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.core.infra.utils.JsonService;

@Configuration
@Service
public abstract class Producer<TDto extends BaseDto, PK> implements IProducer<TDto, PK> {

	@Autowired
	protected RabbitTemplate rabbitTemplate;

	@Autowired
	protected JsonService jsonService;

	@Async
	public void sendMessage(String exchangeName, String routeKey, TDto obj) {
		convertAndSend(exchangeName, routeKey, serialize(obj));
	}

	private String serialize(TDto obj) {
		try {
			return jsonService.getObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void convertAndSend(String exchangeName, String routeKey, String json) {
		rabbitTemplate.convertAndSend(exchangeName, routeKey, json, new CustomMessagePostProcessor(1000 * 60 * 60));
	}
}
