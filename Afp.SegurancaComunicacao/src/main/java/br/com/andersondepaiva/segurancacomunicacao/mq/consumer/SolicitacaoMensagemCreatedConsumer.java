package br.com.andersondepaiva.segurancacomunicacao.mq.consumer;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.Consumer;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.ISolicitacaoMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.mq.consumer.interfaces.ISolicitacaoMensagemConsumer;

@Configuration
@Service
public class SolicitacaoMensagemCreatedConsumer extends Consumer<SolicitacaoMensagemDto, UUID> implements ISolicitacaoMensagemConsumer {

	@Value("${app.mq.solicitacao-mensagem.queue-event-created:solicitacaomensagem-event-created}")
	private String queue; 
	
	@Value("${app.mq.solicitacao-mensagem.routing-key-event-create:solicitacao-mensagem.event.created}")
	private String routingKey;
	
	@Autowired
	private ISolicitacaoMensagemBusiness solicitacaoMensagemBusiness;

	@Bean
	public Queue solicitacaoMensagemEventCreatedQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingSolicitacaoMensagemCreatedConsumer(Queue solicitacaoMensagemEventCreatedQueue, Exchange solicitacaoMensagemExchange) {
		return BindingBuilder.bind(solicitacaoMensagemEventCreatedQueue).to(solicitacaoMensagemExchange).with(routingKey).noargs();
	}

	@Override
	@RabbitListener(queues = "#{solicitacaoMensagemEventCreatedQueue.getName()}")
	public void receive(String json) throws Exception {
		SolicitacaoMensagemDto dto = jsonService.getObjectMapper().readValue(json, SolicitacaoMensagemDto.class);
		solicitacaoMensagemBusiness.sendMessages(dto);
	}
}