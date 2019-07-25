package br.com.andersondepaiva.cadastroativos.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import br.com.andersondepaiva.core.dto.BaseDto;

@SuppressWarnings("serial")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class EquipamentoDto extends BaseDto {

	@NotEmpty(message = "Descrição é obrigatório")
	private String descricao;
	
	@Valid
	private CategoriaDto categoria;
	
	@NotNull(message = "Data de fabricação é obrigatório")
	private LocalDate dataFabricacao;
	
	@NotEmpty(message = "Número de série é obrigatório")
	private String numeroSerie;
}
