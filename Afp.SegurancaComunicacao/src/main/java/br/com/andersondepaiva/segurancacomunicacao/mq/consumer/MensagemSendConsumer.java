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
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.mq.consumer.interfaces.IMensagemConsumer;

@Configuration
@Service
public class MensagemSendConsumer extends Consumer<MensagemDto, UUID> implements IMensagemConsumer {

	@Value("${app.mq.mensagem.queue-cmd-send:mensagem-cmd-send}")
	private String queue; 
	
	@Value("${app.mq.mensagem.routing-key-cmd-send:mensagem.cmd.send}")
	private String routingKey;
	
	@Autowired
	private IMensagemBusiness mensagemBusiness;

	@Bean
	public Queue mensagemCmdSendQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingMensagemSendConsumer(Queue mensagemCmdSendQueue, Exchange mensagemExchange) {
		return BindingBuilder.bind(mensagemCmdSendQueue).to(mensagemExchange).with(routingKey).noargs();
	}

	@Override
	@RabbitListener(queues = "#{mensagemCmdSendQueue.getName()}")
	public void receive(String json) throws Exception {
		MensagemDto dto = jsonService.getObjectMapper().readValue(json, MensagemDto.class);
		mensagemBusiness.send(dto);
	}
}