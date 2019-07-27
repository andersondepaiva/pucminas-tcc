package br.com.andersondepaiva.segurancacomunicacao.dto;

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
public class EscopoDto extends BaseDto {
	
	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;
}
