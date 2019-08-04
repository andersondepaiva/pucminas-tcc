package br.com.andersondepaiva.monitoramentobarragens.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.BarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Barragem;
import br.com.andersondepaiva.monitoramentobarragens.model.MetodoConstrutivo;
import br.com.andersondepaiva.monitoramentobarragens.model.Minerio;
import br.com.andersondepaiva.monitoramentobarragens.repository.IBarragemRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMetodoConstrutivoRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMinerioRepository;

@Service
public class BarragemBusiness extends Business<Barragem, String, BarragemDto>
		implements IBarragemBusiness {

	@Autowired
	private IMinerioRepository minerioRepository;

	@Autowired
	private IMetodoConstrutivoRepository metodoConstrutivoRepository;

	@Autowired
	public BarragemBusiness(IBarragemRepository baseRepository) {
		super(baseRepository);
	}

	@Override
	public BarragemDto save(BarragemDto dto)
			throws ReflectiveOperationException {

		Optional<Minerio> minerioOptional = minerioRepository.findById(dto
				.getMinerioPrincipalId());

		if (!minerioOptional.isPresent())
			throw new BusinessException("Minério não encontrado");

		Optional<MetodoConstrutivo> metodoConstrutivoOptional = metodoConstrutivoRepository
				.findById(dto.getMetodoConstrutivoId());

		if (!metodoConstrutivoOptional.isPresent())
			throw new BusinessException("Método Construtivo não encontrado");

		Barragem entity = modelMapper.map(dto, Barragem.class);
		entity.setMinerioPrincipal(minerioOptional.get());
		entity.setMetodoConstrutivo(metodoConstrutivoOptional.get());

		return modelMapper.map(baseRepository.save(entity), BarragemDto.class);
	}
}
