package br.com.andersondepaiva.cadastroativos.model;

import java.time.LocalDate;

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
@Document(collection = "equipamentos")
public class Equipamento extends BaseModel {

	private String descricao;
	
	private Categoria categoria;
	
	private LocalDate dataFabricacao;
	
	private String numeroSerie;
}
