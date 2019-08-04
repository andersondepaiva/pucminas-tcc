package br.com.andersondepaiva.segurancacomunicacao.business.interfaces;

import java.util.List;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoDto;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoFilter;
import br.com.andersondepaiva.segurancacomunicacao.model.Escopo;

public interface IEscopoBusiness extends IBusiness<Escopo, String, EscopoDto> {
	Escopo getEscopoById(String escopoId);

	List<EscopoDto> getAll(EscopoFilter escopoFilter);
}
