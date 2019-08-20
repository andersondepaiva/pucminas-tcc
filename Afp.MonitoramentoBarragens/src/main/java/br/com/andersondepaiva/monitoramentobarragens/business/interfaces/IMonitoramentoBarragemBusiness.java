package br.com.andersondepaiva.monitoramentobarragens.business.interfaces;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.RelatorioMonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.MonitoramentoBarragem;

public interface IMonitoramentoBarragemBusiness extends IBusiness<MonitoramentoBarragem, String, MonitoramentoBarragemDto> {
	
	RelatorioMonitoramentoBarragemDto getAllByBarragem(String id);
}
