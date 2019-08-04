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
@Document(collection = "barragens")
public class Barragem extends BaseModel {

	private String descricao;

	private Double latitude;

	private Double longitude;

	private Minerio minerioPrincipal;

	private Double altura;

	private Double volume;

	private MetodoConstrutivo metodoConstrutivo;

	private Risco risco;

	private DanoPotencial danoPotencial;
}
