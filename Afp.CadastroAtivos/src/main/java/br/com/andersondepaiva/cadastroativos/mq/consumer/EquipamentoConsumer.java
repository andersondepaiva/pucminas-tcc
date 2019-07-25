package br.com.andersondepaiva.cadastroativos.mq.consumer;

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

import br.com.andersondepaiva.cadastroativos.business.interfaces.IEquipamentoBusiness;
import br.com.andersondepaiva.cadastroativos.dto.EquipamentoDto;
import br.com.andersondepaiva.cadastroativos.mq.consumer.interfaces.IEquipamentoConsumer;
import br.com.andersondepaiva.core.mq.Consumer;

@Configuration
@Service
public class EquipamentoConsumer extends Consumer<EquipamentoDto, UUID> implements IEquipamentoConsumer {

	@Value("${app.mq.profissao.queue:profissao-cmd-queue}")
	private String queue; 
	
	@Value("${app.mq.profissao.routing-key-cmd-create:profissao.cmd.create}")
	private String routingKey;
	
	@Autowired
	private IEquipamentoBusiness profissaoBusiness;

	@Bean
	public Queue profissaoCmdQueue() {
		return buildQueue(queue);
	}

	@Bean
	public Binding bindingProfissaoConsumer(Queue profissaoCmdQueue, Exchange profissaoExchange) {
		return BindingBuilder.bind(profissaoCmdQueue).to(profissaoExchange).with(routingKey).noargs();
	}

	@Override
	@RabbitListener(queues = "#{profissaoCmdQueue.getName()}")
	public void receive(String json) throws Exception {
		EquipamentoDto dto = jsonService.getObjectMapper().readValue(json, EquipamentoDto.class);
		profissaoBusiness.save(dto);
	}
}
