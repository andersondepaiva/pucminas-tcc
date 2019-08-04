package br.com.andersondepaiva.monitoramentobarragens.model;

import java.math.BigDecimal;

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
@Document(collection = "monitoramento")
public class MonitoramentoBarragem extends BaseModel {

	private String barragemId;
	
	private BigDecimal volumeAtual;
	
	private BigDecimal deslocamentoSolo;
}
