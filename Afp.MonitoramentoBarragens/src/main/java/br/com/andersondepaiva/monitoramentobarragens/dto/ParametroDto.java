package br.com.andersondepaiva.monitoramentobarragens.dto;

import java.math.BigDecimal;

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
public class ParametroDto extends BaseDto {

	@NotEmpty(message = "Indicador é obrigatório")
	private String indicador;

	@NotNull(message = "Parâmetros de comparação são obrigatórios")
	private BigDecimal maiorQue;

	@NotNull(message = "Parâmetros de comparação são obrigatórios")
	private BigDecimal menorQue;

	private String mensagemAlerta;
}
