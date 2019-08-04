package br.com.andersondepaiva.monitoramentobarragens.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.Producer;
import br.com.andersondepaiva.monitoramentobarragens.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.monitoramentobarragens.mq.producer.interfaces.ISolicitacaoMensagemCmdCreateProducer;

@Configuration
@Service
public class SolicitacaoMensagemCmdCreateProducer extends
		Producer<SolicitacaoMensagemDto, UUID> implements
		ISolicitacaoMensagemCmdCreateProducer {
	
	@Value("${app.mq.solicitacao-mensagem.queue-cmd-create:solicitacaomensagem-cmd-create}")
	private String queue; 
	
	@Value("${app.mq.solicitacao-mensagem.routing-key-cmd-create:solicitacao-mensagem.cmd.create}")
	private String routingKey;
	
	@Value("${app.mq.solicitacao-mensagem.exchange:seguranca-comunicacao.solicitacao-mensagem}")
	private String exchange;

	public String getRoutingKeyCmdCreate() {
		return routingKey;
	}

	@Bean
	public Exchange solicitacaoMensagemExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange(exchange);
	}
}


