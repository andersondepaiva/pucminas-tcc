package br.com.andersondepaiva.monitoramentobarragens.mq.consumer;

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
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IParametroAlertaBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.monitoramentobarragens.mq.consumer.interfaces.IMonitoramentoBarragemCreatedConsumer;

@Configuration
@Service
public class MonitoramentoBarragemCreatedConsumer extends
		Consumer<SolicitacaoMensagemDto, UUID> implements
		IMonitoramentoBarragemCreatedConsumer {

	@Value("${app.mq.monitoramentobarragem.queue-event-created:monitoramentobarragem-event-created}")
	private String queue;

	@Value("${app.mq.monitoramentobarragem.routing-key-event-created:monitoramentobarragem.event.created}")
	private String routingKey;

	@Autowired
	private IParametroAlertaBusiness parametroAlertaBusiness;

	@Bean
	public Queue monitoramentoBarragemEventCreatedQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingSolicitacaoMensagemCreatedConsumer(
			Queue monitoramentoBarragemEventCreatedQueue,
			Exchange monitoramentoBarragemExchange) {
		return BindingBuilder.bind(monitoramentoBarragemEventCreatedQueue)
				.to(monitoramentoBarragemExchange).with(routingKey).noargs();
	}

	@RabbitListener(queues = "#{monitoramentoBarragemEventCreatedQueue.getName()}")
	public void receive(String json) throws Exception {
		MonitoramentoBarragemDto dto = jsonService.getObjectMapper().readValue(
				json, MonitoramentoBarragemDto.class);
		parametroAlertaBusiness.verifyParametrosAlerta(dto);
	}
}