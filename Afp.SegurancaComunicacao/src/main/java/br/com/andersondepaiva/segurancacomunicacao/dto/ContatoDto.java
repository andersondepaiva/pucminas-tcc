package br.com.andersondepaiva.segurancacomunicacao.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class ContatoDto extends BaseDto {
	
	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;
	
	@NotNull(message = "Escopo é obrigatório")
	private String escopoId;
	
	private String ddi;
	
	private String ddd;
	
	@NotEmpty(message = "Número do celular é obrigatório")
	private String numeroCelular;
}
