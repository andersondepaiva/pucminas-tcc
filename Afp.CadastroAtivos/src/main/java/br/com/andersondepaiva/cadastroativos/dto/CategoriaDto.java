package br.com.andersondepaiva.cadastroativos.dto;

import javax.validation.constraints.NotEmpty;

import br.com.andersondepaiva.core.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class CategoriaDto extends BaseDto {

	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;
}
