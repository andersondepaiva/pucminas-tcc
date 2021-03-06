package br.com.andersondepaiva.segurancacomunicacao.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import br.com.andersondepaiva.core.dto.BaseDto;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class SolicitacaoMensagemDto extends BaseDto {
	
	@NotEmpty(message = "Motivo é obrigatório")
	private String motivo;
	
	@NotEmpty(message = "Escopos são obrigatórios")
	private List<String> escoposId;
	
	@NotEmpty(message = "Mensagem é obrigatório")
	private String mensagem;
}
