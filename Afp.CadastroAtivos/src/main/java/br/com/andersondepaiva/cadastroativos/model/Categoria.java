package br.com.andersondepaiva.cadastroativos.model;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.andersondepaiva.core.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuppressWarnings("serial")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "categorias")
public class Categoria extends BaseModel {

	private String descricao;
}
