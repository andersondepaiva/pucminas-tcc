package br.com.andersondepaiva.segurancacomunicacao.business.interfaces;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.ContatoDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Contato;

public interface IContatoBusiness extends IBusiness<Contato, String, ContatoDto> {
}
