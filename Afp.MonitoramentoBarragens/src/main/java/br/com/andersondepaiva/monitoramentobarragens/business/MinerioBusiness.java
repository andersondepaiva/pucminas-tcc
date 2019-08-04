package br.com.andersondepaiva.monitoramentobarragens.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMinerioBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MinerioDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Minerio;
import br.com.andersondepaiva.monitoramentobarragens.repository.IMinerioRepository;

@Service
public class MinerioBusiness extends Business<Minerio, String, MinerioDto>
		implements IMinerioBusiness {

	@Autowired
	public MinerioBusiness(IMinerioRepository baseRepository) {
		super(baseRepository);
	}
}
