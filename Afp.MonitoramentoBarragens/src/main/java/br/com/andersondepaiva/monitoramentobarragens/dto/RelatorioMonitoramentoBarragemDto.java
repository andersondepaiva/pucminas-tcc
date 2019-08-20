package br.com.andersondepaiva.monitoramentobarragens.dto;

import java.util.List;

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
public class RelatorioMonitoramentoBarragemDto extends BaseDto {

	private BarragemDto barragem;
	
	private List<MonitoramentoBarragemDto> monitoramentos;
}
