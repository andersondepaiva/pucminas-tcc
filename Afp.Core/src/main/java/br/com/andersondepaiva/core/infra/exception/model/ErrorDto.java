package br.com.andersondepaiva.core.infra.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ErrorDto {

	private String defaultMessage;

}
