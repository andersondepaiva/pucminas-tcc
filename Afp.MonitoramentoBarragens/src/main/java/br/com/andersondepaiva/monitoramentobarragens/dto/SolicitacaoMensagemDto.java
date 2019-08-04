package br.com.andersondepaiva.monitoramentobarragens.dto;

import java.util.List;

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
public class SolicitacaoMensagemDto extends BaseDto {

	private String motivo;

	private List<String> escoposId;

	private String mensagem;
}
