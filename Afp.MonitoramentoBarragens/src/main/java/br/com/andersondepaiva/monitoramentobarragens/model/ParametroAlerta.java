package br.com.andersondepaiva.monitoramentobarragens.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.andersondepaiva.core.model.BaseModel;

@SuppressWarnings("serial")
@Getter
@Setter
@SuperBuilder
@Document(collection = "parametros-alerta")
public class ParametroAlerta extends BaseModel {

	private String descricao;

	private List<String> barragensId;

	private List<Parametro> parametros;

	private String mensagemAlerta;

	private List<String> escoposId;

	public ParametroAlerta() {
		this.barragensId = new ArrayList<String>();
	}
}
