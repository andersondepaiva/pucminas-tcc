package br.com.andersondepaiva.gateway.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Route {

	@NotEmpty
	private String uri;

	private String methods;

	private String roles;

}
