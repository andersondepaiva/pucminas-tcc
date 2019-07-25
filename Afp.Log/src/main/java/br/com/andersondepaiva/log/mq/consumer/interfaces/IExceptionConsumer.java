package br.com.andersondepaiva.log.mq.consumer.interfaces;

import java.util.UUID;

import br.com.andersondepaiva.core.infra.exception.model.ExceptionDto;
import br.com.andersondepaiva.core.mq.IConsumer;

public interface IExceptionConsumer extends IConsumer<ExceptionDto, UUID> {

}
