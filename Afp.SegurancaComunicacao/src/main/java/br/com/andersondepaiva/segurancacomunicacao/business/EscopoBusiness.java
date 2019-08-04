package br.com.andersondepaiva.segurancacomunicacao.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IEscopoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoDto;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoFilter;
import br.com.andersondepaiva.segurancacomunicacao.model.Escopo;
import br.com.andersondepaiva.segurancacomunicacao.repository.IEscopoRepository;

@Service
public class EscopoBusiness extends Business<Escopo, String, EscopoDto>
		implements IEscopoBusiness {

	@Autowired
	public EscopoBusiness(IEscopoRepository baseRepository) {
		super(baseRepository);
	}

	public Escopo getEscopoById(String escopoId) {
		Optional<Escopo> optionalEscopo = baseRepository.findById(escopoId);

		if (!optionalEscopo.isPresent())
			throw new BusinessException(String.format(
					"Escopo %s não encontrado", escopoId));

		return optionalEscopo.get();
	}

	public List<EscopoDto> getAll(EscopoFilter escopoFilter) {
		Query query = new Query();

		if (escopoFilter.getIds() != null && !escopoFilter.getIds().isEmpty()) {
			Criteria criteria = Criteria.where("id").in(escopoFilter.getIds());
			query.addCriteria(criteria);
		}

		List<Escopo> resultList = mongoTemplate.find(query, Escopo.class);

		List<EscopoDto> escopoDtos = new ArrayList<EscopoDto>();
		
		for (Escopo escopo : resultList) {
			escopoDtos.add(modelMapper.map(escopo, EscopoDto.class));
		}
		
		
		return escopoDtos;
	}
}
