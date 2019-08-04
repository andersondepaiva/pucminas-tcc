package br.com.andersondepaiva.segurancacomunicacao.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class EscopoFilter {
	private List<String> ids;
}
