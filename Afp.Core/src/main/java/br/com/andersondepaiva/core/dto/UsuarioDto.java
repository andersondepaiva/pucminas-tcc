package br.com.andersondepaiva.core.dto;

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
public class UsuarioDto extends BaseDto {

	private String login;

	private PessoaDto pessoa;
}
