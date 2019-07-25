package br.com.andersondepaiva.cadastroativos.mq.producer;
/*package br.com.andersondepaiva.cadastroativos.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.cadastroativos.dto.EquipamentoDto;
import br.com.andersondepaiva.cadastroativos.mq.producer.interfaces.IProfissaoProducer;
import br.com.andersondepaiva.core.mq.Producer;

@Configuration
@Service
public class ProfissaoProducer extends Producer<EquipamentoDto, UUID> implements IProfissaoProducer {

	@Value("${app.mq.profissao.exchange:profissao}")
	private String exchange;
	
	@Value("${app.mq.profissao.routing-key-event-create:profissao.event.created}")
	private String routingKeyEventCreate;
	
	public String getRoutingKeyEventCreate() {
		return routingKeyEventCreate;
	}

	@Bean
	public Exchange profissaoExchange() {
		// TODO Auto-generated method stub
		return new TopicExchange(exchange);
	}
}
*/