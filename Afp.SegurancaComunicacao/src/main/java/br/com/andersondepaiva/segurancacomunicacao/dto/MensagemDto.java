package br.com.andersondepaiva.segurancacomunicacao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.segurancacomunicacao.model.SolicitacaoMensagem;
import br.com.andersondepaiva.segurancacomunicacao.model.Status;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class MensagemDto extends BaseDto {
	
	private SolicitacaoMensagem solicitacaoMensagem;
	
	private ContatoDto contato;
	
	private Status status;
}
