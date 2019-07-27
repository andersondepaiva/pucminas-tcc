package br.com.andersondepaiva.segurancacomunicacao.mq.producer.interfaces;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.IProducer;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;

@Service
public interface IMensagemProducer extends IProducer<MensagemDto, UUID> {

	Exchange mensagemExchange();

	String getRoutingKeyCmdSend();
}
