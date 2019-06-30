package br.com.andersondepaiva.gateway.model;

import br.com.andersondepaiva.core.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class Requisicao extends BaseModel {

	private String corpoRequisicao;
	private String metodo;
	private String url;
	private String corpoResposta;
	private Integer httpStatusCode;
	private Integer tempoRequisicao;
}
