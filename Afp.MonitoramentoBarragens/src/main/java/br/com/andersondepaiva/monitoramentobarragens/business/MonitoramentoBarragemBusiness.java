package br.com.andersondepaiva.monitoramentobarragens.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMonitoramentoBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.MonitoramentoBarragem;
import br.com.andersondepaiva.monitoramentobarragens.mq.producer.interfaces.IMonitoramentoBarragemProducer;
import br.com.andersondepaiva.monitoramentobarragens.repository.IBarragemRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMonitoramentoBarragemRepository;

@Service
public class MonitoramentoBarragemBusiness extends
		Business<MonitoramentoBarragem, String, MonitoramentoBarragemDto>
		implements IMonitoramentoBarragemBusiness {

	@Autowired
	private IBarragemRepository barragemRepository;

	@Autowired
	private IMonitoramentoBarragemProducer monitoramentoBarragemProducer;

	@Autowired
	public MonitoramentoBarragemBusiness(
			IMonitoramentoBarragemRepository baseRepository) {
		super(baseRepository);
	}

	@Override
	public MonitoramentoBarragemDto save(MonitoramentoBarragemDto dto)
			throws ReflectiveOperationException {

		if (!barragemRepository.existsById(dto.getBarragemId()))
			throw new BusinessException("Barragem não encontrada");

		MonitoramentoBarragem entity = modelMapper.map(dto,
				MonitoramentoBarragem.class);

		MonitoramentoBarragemDto monitoramentoBarragemDto = modelMapper.map(
				baseRepository.save(entity), MonitoramentoBarragemDto.class);

		monitoramentoBarragemProducer.sendMessage(monitoramentoBarragemProducer
				.monitoramentoBarragemExchange().getName(),
				monitoramentoBarragemProducer.getRoutingKeyEventCreated(),
				monitoramentoBarragemDto);

		return monitoramentoBarragemDto;
	}
}
