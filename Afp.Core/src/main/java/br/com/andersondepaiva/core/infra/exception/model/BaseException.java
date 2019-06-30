package br.com.andersondepaiva.core.infra.exception.model;

import java.time.LocalDateTime;

import br.com.andersondepaiva.core.dto.BaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseException extends BaseDto {
	
	protected LocalDateTime timestamp;
	protected String message;
}
