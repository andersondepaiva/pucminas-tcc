package br.com.andersondepaiva.core.mq;

import org.springframework.scheduling.annotation.Async;

import br.com.andersondepaiva.core.dto.BaseDto;

public interface IProducer<TDto extends BaseDto, PK> {

	@Async
	void sendMessage(String exchangeName, String routeKey, TDto obj);
}
