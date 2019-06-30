package br.com.andersondepaiva.core.mq;

import br.com.andersondepaiva.core.dto.BaseDto;

public interface IConsumer<TDto extends BaseDto, PK> {
	
	void receive(String json) throws Exception;

}
