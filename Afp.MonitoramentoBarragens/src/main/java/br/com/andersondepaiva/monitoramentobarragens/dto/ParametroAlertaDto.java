package br.com.andersondepaiva.monitoramentobarragens.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import br.com.andersondepaiva.core.dto.BaseDto;

@SuppressWarnings("serial")
@Setter
@Getter
@SuperBuilder
public class ParametroAlertaDto extends BaseDto {

	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;

	private List<String> barragensId;

	@NotEmpty(message = "Parâmetros são obrigatórios")
	private List<ParametroDto> parametros;

	@NotEmpty(message = "Mensagem de Alerta é obrigatório")
	private String mensagemAlerta;

	@NotEmpty(message = "Escopos de Aviso são obrigatórios")
	private List<String> escoposId;

	public ParametroAlertaDto() {
		this.barragensId = new ArrayList<String>();
	}
}
