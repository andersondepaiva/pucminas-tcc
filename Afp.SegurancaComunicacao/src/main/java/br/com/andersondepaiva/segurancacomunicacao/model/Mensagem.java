package br.com.andersondepaiva.segurancacomunicacao.model;

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
@Document(collection = "mensagens")
public class Mensagem extends BaseModel {
	
	private SolicitacaoMensagem solicitacaoMensagem;
	
	private Escopo escopo;
}
