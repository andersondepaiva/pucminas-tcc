package br.com.andersondepaiva.monitoramentobarragens.business.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.BarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Barragem;

public interface IBarragemBusiness extends IBusiness<Barragem, String, BarragemDto> {
	Page<BarragemDto> getAll(BarragemDto filter, Pageable pageable);
}
