package br.com.andersondepaiva.segurancacomunicacao.business.interfaces;

import java.io.IOException;

import com.nexmo.client.NexmoClientException;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Mensagem;

public interface IMensagemBusiness extends
		IBusiness<Mensagem, String, MensagemDto> {
	
	void send(MensagemDto message) throws IOException, NexmoClientException;
}
