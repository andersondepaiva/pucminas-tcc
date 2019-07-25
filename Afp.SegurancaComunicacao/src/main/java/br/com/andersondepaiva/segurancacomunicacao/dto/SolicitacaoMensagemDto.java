package br.com.andersondepaiva.segurancacomunicacao.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Escopo;
import br.com.andersondepaiva.segurancacomunicacao.model.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class SolicitacaoMensagemDto extends BaseDto {
	
	@NotEmpty(message = "Motivo é obrigatório")
	private String motivo;
	
	@NotEmpty(message = "Escopo é obrigatório")
	private List<Escopo> escopos;
	
	private Status status;
}
