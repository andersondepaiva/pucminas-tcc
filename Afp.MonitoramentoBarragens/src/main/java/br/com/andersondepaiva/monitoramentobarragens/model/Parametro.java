package br.com.andersondepaiva.monitoramentobarragens.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Parametro {

	private String indicador;

	private BigDecimal maiorQue;

	private BigDecimal menorQue;
	
	private String mensagemAlerta;
}
