package br.com.andersondepaiva.core.infra.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ExceptionDto extends BaseException {

	@JsonInclude(Include.NON_NULL)
	private String api;

	@JsonInclude(Include.NON_NULL)
	private Exception exception;

	@JsonInclude(Include.NON_NULL)
	private String body;

	@JsonInclude(Include.NON_NULL)
	private String uri;

	@JsonInclude(Include.NON_NULL)
	private String method;
}