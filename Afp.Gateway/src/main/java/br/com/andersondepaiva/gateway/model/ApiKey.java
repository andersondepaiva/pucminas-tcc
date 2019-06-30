package br.com.andersondepaiva.gateway.model;

import javax.validation.constraints.NotEmpty;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiKey {

	@NotEmpty
	private String api;

	@NotEmpty
	private String key;

	private String header;

	public String getHeader() {
		return Strings.isNullOrEmpty(header) ? "x-api-key" : header;
	}
}
