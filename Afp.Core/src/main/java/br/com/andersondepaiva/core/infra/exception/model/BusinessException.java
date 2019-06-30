package br.com.andersondepaiva.core.infra.exception.model;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}

}
