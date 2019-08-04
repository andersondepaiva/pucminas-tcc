package br.com.andersondepaiva.monitoramentobarragens.business.interfaces;

import br.com.andersondepaiva.core.business.IBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.ParametroAlertaDto;
import br.com.andersondepaiva.monitoramentobarragens.model.ParametroAlerta;

public interface IParametroAlertaBusiness extends
		IBusiness<ParametroAlerta, String, ParametroAlertaDto> {

	void verifyParametrosAlerta(
			MonitoramentoBarragemDto monitoramentoBarragemDto) throws Exception;
}
