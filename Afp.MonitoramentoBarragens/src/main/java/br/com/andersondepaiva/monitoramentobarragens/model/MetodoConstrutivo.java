package br.com.andersondepaiva.monitoramentobarragens.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.andersondepaiva.core.model.BaseModel;

@SuppressWarnings("serial")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "metodos-construtivos")
public class MetodoConstrutivo extends BaseModel {
	
	private String descricao;
	
}
