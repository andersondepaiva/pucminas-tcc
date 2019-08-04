package br.com.andersondepaiva.monitoramentobarragens.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.monitoramentobarragens.model.DanoPotencial;
import br.com.andersondepaiva.monitoramentobarragens.model.Risco;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class BarragemDto extends BaseDto {
	
	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;

	@NotNull(message = "Latitude é obrigatório")
	private Double latitude;

	@NotNull(message = "Longitude é obrigatório")
	private Double longitude;

	@NotEmpty(message = "Minério Principal é obrigatório")
	private String minerioPrincipalId;

	@NotNull(message = "Altura é obrigatório")
	private Double altura;

	@NotNull(message = "Volume é obrigatório")
	private Double volume;

	@NotEmpty(message = "Método Construtivo é obrigatório")
	private String metodoConstrutivoId;

	@NotNull(message = "Grau de Risco é obrigatório")
	private Risco risco;

	@NotNull(message = "Dano Potêncial é obrigatório")
	private DanoPotencial danoPotencial;
}
