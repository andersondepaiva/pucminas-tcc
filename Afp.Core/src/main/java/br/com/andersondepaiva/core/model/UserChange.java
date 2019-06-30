package br.com.andersondepaiva.core.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class UserChange implements Serializable {

	@Id
	private String id;

	private String nome;
}
