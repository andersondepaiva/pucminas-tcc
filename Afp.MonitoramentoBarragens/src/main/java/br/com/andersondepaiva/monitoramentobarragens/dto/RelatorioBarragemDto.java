package br.com.andersondepaiva.monitoramentobarragens.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class RelatorioBarragemDto extends BarragemDto {
	
	private MinerioDto minerioPrincipal;
}
