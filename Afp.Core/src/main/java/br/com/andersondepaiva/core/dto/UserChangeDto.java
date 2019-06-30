package br.com.andersondepaiva.core.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class UserChangeDto implements Serializable {

	@JsonInclude(Include.NON_NULL)
	private String id;

	@JsonInclude(Include.NON_NULL)
	private String nome;
}
