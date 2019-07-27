package br.com.andersondepaiva.segurancacomunicacao.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.Producer;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.mq.producer.interfaces.IMensagemProducer;

@Configuration
@Service
public class MensagemProducer extends Producer<MensagemDto, UUID> implements IMensagemProducer {

	@Value("${app.mq.mensagem.exchange:seguranca-comunicacao.mensagem}")
	private String exchange;
	
	@Value("${app.mq.solicitacao-mensagem.routing-key-cmd-send:mensagem.cmd.send}")
	private String routingKeyCmdSend;
	
	public String getRoutingKeyCmdSend() {
		return routingKeyCmdSend;
	}

	@Bean
	public Exchange mensagemExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange(exchange);
	}
}
