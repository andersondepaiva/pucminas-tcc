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
import br.com.andersondepaiva.segurancacomunicacao.mq.consumer.interfaces.ISolicitacaoMensagemCmdCreateConsumer;

@Configuration
@Service
public class SolicitacaoMensagemCmdCreateConsumer extends Consumer<SolicitacaoMensagemDto, UUID> implements ISolicitacaoMensagemCmdCreateConsumer {

	@Value("${app.mq.solicitacao-mensagem.queue-cmd-create:solicitacaomensagem-cmd-create}")
	private String queue; 
	
	@Value("${app.mq.solicitacao-mensagem.routing-key-cmd-create:solicitacao-mensagem.cmd.create}")
	private String routingKey;
	
	@Autowired
	private ISolicitacaoMensagemBusiness solicitacaoMensagemBusiness;

	@Bean
	public Queue solicitacaoMensagemCmdCreateQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingSolicitacaoMensagemCmdCreateConsumer(Queue solicitacaoMensagemCmdCreateQueue, Exchange solicitacaoMensagemExchange) {
		return BindingBuilder.bind(solicitacaoMensagemCmdCreateQueue).to(solicitacaoMensagemExchange).with(routingKey).noargs();
	}

	@Override
	@RabbitListener(queues = "#{solicitacaoMensagemCmdCreateQueue.getName()}")
	public void receive(String json) throws Exception {
		SolicitacaoMensagemDto dto = jsonService.getObjectMapper().readValue(json, SolicitacaoMensagemDto.class);
		solicitacaoMensagemBusiness.save(dto);
	}
}