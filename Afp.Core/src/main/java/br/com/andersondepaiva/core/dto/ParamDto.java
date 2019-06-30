package br.com.andersondepaiva.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class ParamDto {
	private String field;
	private Object value;
	private Comparasion comparasion;
	private boolean not;
}
