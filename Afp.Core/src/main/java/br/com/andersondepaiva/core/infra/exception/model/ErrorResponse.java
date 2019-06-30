package br.com.andersondepaiva.core.infra.exception.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ErrorResponse extends BaseException {

	private String details;
	
	private List<ErrorDto> errors;
}
