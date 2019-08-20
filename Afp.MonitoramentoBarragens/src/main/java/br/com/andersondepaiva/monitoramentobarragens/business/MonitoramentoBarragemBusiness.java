package br.com.andersondepaiva.monitoramentobarragens.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMonitoramentoBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.RelatorioBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.RelatorioMonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Barragem;
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
				super.save(entity), MonitoramentoBarragemDto.class);

		monitoramentoBarragemProducer.sendMessage(monitoramentoBarragemProducer
				.monitoramentoBarragemExchange().getName(),
				monitoramentoBarragemProducer.getRoutingKeyEventCreated(),
				monitoramentoBarragemDto);

		return monitoramentoBarragemDto;
	}

	public RelatorioMonitoramentoBarragemDto getAllByBarragem(String id) {
		IMonitoramentoBarragemRepository monitoramentoBarragemRepository = (IMonitoramentoBarragemRepository) baseRepository;

		Optional<Barragem> barragemOptional = barragemRepository.findById(id);

		if (!barragemOptional.isPresent())
			throw new BusinessException("Barragem não encontrada");

		Barragem barragem = barragemOptional.get();

		List<MonitoramentoBarragem> monitoramentos = monitoramentoBarragemRepository
				.findAllByBarragemIdAndExcluidoFalse(barragem.getId());

		RelatorioMonitoramentoBarragemDto relatorio = new RelatorioMonitoramentoBarragemDto();

		relatorio.setBarragem(modelMapper.map(barragem,
				RelatorioBarragemDto.class));
		List<MonitoramentoBarragemDto> monitoramentoDtos = new ArrayList<MonitoramentoBarragemDto>();
		for (MonitoramentoBarragem monitoramentoBarragem : monitoramentos) {
			monitoramentoDtos.add(modelMapper.map(monitoramentoBarragem,
					MonitoramentoBarragemDto.class));
		}
		
		relatorio.setMonitoramentos(monitoramentoDtos);
		
		return relatorio;
	}
}
