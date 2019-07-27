package br.com.andersondepaiva.segurancacomunicacao.business.interfaces;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Escopo;

public interface IEscopoBusiness extends IBusiness<Escopo, String, EscopoDto> {
	Escopo getEscopoById(String escopoId);
}
