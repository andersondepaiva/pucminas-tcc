package br.com.andersondepaiva.log.dto;

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
public class RequisicaoDto extends BaseDto {

	private String corpoRequisicao;
	private String metodo;
	private String url;
	private String corpoResposta;
	private Integer httpStatusCode;
	private Integer tempoRequisicao;
}
