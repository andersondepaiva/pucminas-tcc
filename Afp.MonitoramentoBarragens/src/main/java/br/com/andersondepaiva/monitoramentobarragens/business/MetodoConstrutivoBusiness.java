package br.com.andersondepaiva.monitoramentobarragens.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMetodoConstrutivoBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MetodoConstrutivoDto;
import br.com.andersondepaiva.monitoramentobarragens.model.MetodoConstrutivo;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMetodoConstrutivoRepository;

@Service
public class MetodoConstrutivoBusiness extends Business<MetodoConstrutivo, String, MetodoConstrutivoDto>
		implements IMetodoConstrutivoBusiness {

	@Autowired
	public MetodoConstrutivoBusiness(IMetodoConstrutivoRepository baseRepository) {
		super(baseRepository);
	}
}
