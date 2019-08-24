package br.com.andersondepaiva.monitoramentobarragens.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.dto.Comparasion;
import br.com.andersondepaiva.core.dto.ParamDto;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.BarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Barragem;
import br.com.andersondepaiva.monitoramentobarragens.model.MetodoConstrutivo;
import br.com.andersondepaiva.monitoramentobarragens.model.Minerio;
import br.com.andersondepaiva.monitoramentobarragens.repository.IBarragemRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMetodoConstrutivoRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMinerioRepository;

import com.google.common.base.Strings;

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
	
	public Page<BarragemDto> getAll(BarragemDto filter, Pageable pageable) {

		Page<Barragem> dataSet = super.filterAndPaginateModel(buildParameters(filter), pageable, null);

		if (dataSet.hasContent()) {
			List<BarragemDto> dtos = new ArrayList<BarragemDto>();
			dataSet.getContent().stream().forEach(model -> {
				dtos.add((BarragemDto) modelMapper.map(model, BarragemDto.class));
			});

			return new PageImpl<BarragemDto>(dtos, pageable, dataSet.getTotalElements());
		}

		return new PageImpl<BarragemDto>(new ArrayList<BarragemDto>());
	}
	
	private List<ParamDto> buildParameters(BarragemDto filters) {
		List<ParamDto> params = new ArrayList<ParamDto>();

		params.add(ParamDto.builder().comparasion(Comparasion.EQUALS).field("excluido").value(false).build());

		if (filters == null) {
			return params;
		}

		if (!Strings.isNullOrEmpty(filters.getDescricao())) {
			params.add(ParamDto.builder().comparasion(Comparasion.LIKE).field("descricao").value(filters.getDescricao()).build());
		}

		return params;
	}
}
