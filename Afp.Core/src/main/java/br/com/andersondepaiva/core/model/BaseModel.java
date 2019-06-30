package br.com.andersondepaiva.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@SuperBuilder
@Getter
@Setter
public abstract class BaseModel implements Serializable {

	@Id
	protected String id;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime dataInclusao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime dataAlteracao;

	private boolean excluido;

	private UserChange incluidoPor;

	private UserChange alteradoPor;

	public BaseModel() {
		this.excluido = false;
	}
}
