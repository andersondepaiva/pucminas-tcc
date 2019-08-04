package br.com.andersondepaiva.monitoramentobarragens.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.Producer;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.mq.producer.interfaces.IMonitoramentoBarragemProducer;

@Configuration
@Service
public class MonitoramentoBarragemProducer extends
		Producer<MonitoramentoBarragemDto, UUID> implements
		IMonitoramentoBarragemProducer {

	@Value("${app.mq.monitoramentobarragem.exchange:monitoramento-barragem.monitoramentobarragem}")
	private String exchange;

	@Value("${app.mq.monitoramentobarragem.routing-key-event-created:monitoramentobarragem.event.created}")
	private String routingKeyEventCreated;

	public String getRoutingKeyEventCreated() {
		return routingKeyEventCreated;
	}

	@Bean
	public Exchange monitoramentoBarragemExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange(exchange);
	}
}
