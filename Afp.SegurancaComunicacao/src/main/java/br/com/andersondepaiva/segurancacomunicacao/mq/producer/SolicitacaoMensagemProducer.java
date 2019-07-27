package br.com.andersondepaiva.segurancacomunicacao.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.Producer;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.mq.producer.interfaces.ISolicitacaoMensagemProducer;

@Configuration
@Service
public class SolicitacaoMensagemProducer extends Producer<SolicitacaoMensagemDto, UUID> implements ISolicitacaoMensagemProducer {

	@Value("${app.mq.solicitacao-mensagem.exchange:seguranca-comunicacao.solicitacao-mensagem}")
	private String exchange;
	
	@Value("${app.mq.solicitacao-mensagem.routing-key-event-create:solicitacao-mensagem.event.created}")
	private String routingKeyEventCreate;
	
	public String getRoutingKeyEventCreate() {
		return routingKeyEventCreate;
	}

	@Bean
	public Exchange solicitacaoMensagemExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange(exchange);
	}
}
