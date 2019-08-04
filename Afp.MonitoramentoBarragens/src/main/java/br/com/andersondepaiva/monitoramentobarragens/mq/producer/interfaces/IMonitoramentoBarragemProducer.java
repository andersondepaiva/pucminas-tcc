package br.com.andersondepaiva.monitoramentobarragens.mq.producer.interfaces;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.IProducer;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;

@Service
public interface IMonitoramentoBarragemProducer extends IProducer<MonitoramentoBarragemDto, UUID> {

	Exchange monitoramentoBarragemExchange();

	String getRoutingKeyEventCreated();
}
