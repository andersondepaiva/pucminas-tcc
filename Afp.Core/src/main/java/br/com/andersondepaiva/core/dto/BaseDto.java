package br.com.andersondepaiva.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class BaseDto implements Serializable {

	@JsonInclude(Include.NON_NULL)
	protected String id;

	@JsonInclude(Include.NON_NULL)
	protected String correlationId;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime dataInclusao;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime dataAlteracao;

	@JsonInclude(Include.NON_NULL)
	private UserChangeDto incluidoPor;

	@JsonInclude(Include.NON_NULL)
	private UserChangeDto alteradoPor;

	@JsonInclude(Include.NON_NULL)
	private Boolean excluido;

}
