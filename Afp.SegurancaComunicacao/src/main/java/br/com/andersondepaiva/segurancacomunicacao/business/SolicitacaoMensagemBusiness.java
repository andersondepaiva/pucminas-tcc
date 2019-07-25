package br.com.andersondepaiva.segurancacomunicacao.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.ISolicitacaoMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.model.SolicitacaoMensagem;
import br.com.andersondepaiva.segurancacomunicacao.repository.ISolicitacaoMensagemRepository;

@Service
public class SolicitacaoMensagemBusiness extends Business<SolicitacaoMensagem, String, SolicitacaoMensagemDto> implements ISolicitacaoMensagemBusiness {

	@Autowired
	public SolicitacaoMensagemBusiness(ISolicitacaoMensagemRepository baseRepository) {
		super(baseRepository);
	}
}
