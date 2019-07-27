package br.com.andersondepaiva.segurancacomunicacao.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IContatoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IEscopoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.ContatoDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Contato;
import br.com.andersondepaiva.segurancacomunicacao.repository.IContatoRepository;

@Service
public class ContatoBusiness extends Business<Contato, String, ContatoDto>
		implements IContatoBusiness {
	
	@Autowired
	private IEscopoBusiness escopoBusiness;

	@Autowired
	public ContatoBusiness(IContatoRepository baseRepository) {
		super(baseRepository);
	}

	@Override
	public ContatoDto save(ContatoDto dto) {

		escopoBusiness.getEscopoById(dto.getEscopoId());

		Contato contato = modelMapper.map(dto, Contato.class);
		contato = save(contato);

		return modelMapper.map(contato, ContatoDto.class);
	}
}
