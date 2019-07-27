package br.com.andersondepaiva.segurancacomunicacao.model;

import java.util.List;

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
@Document(collection = "solicitacao-mensagem")
public class SolicitacaoMensagem extends BaseModel {
	
	private String motivo;
	
	private String mensagem;
	
	private List<String> escoposId;
}
