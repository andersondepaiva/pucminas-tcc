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
public class MonitoramentoBarragemDto extends BaseDto {

	@NotEmpty(message = "Barragem é obrigatório")
	private String barragemId;

	@NotNull(message = "Volume Atual é obrigatório")
	private BigDecimal volumeAtual;

	@NotNull(message = "Deslocamento do Solo é obrigatório")
	private BigDecimal deslocamentoSolo;
}
