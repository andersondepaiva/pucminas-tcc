package br.com.andersondepaiva.segurancacomunicacao.model;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.andersondepaiva.core.model.BaseModel;

@SuppressWarnings("serial")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "contatos")
public class Contato extends BaseModel {
	
	private String descricao;
	
	private String escopoId;
	
	private String ddi;
	
	private String ddd;
	
	private String numeroCelular;
	
	public String getNumeroCompleto(){
		return String.format("%s%s%s", Optional.ofNullable(ddi).orElse(""), Optional.ofNullable(ddd).orElse(""), numeroCelular);
	}
}
