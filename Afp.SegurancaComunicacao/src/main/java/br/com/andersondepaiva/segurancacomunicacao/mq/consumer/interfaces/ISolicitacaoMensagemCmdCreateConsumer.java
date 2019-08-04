package br.com.andersondepaiva.segurancacomunicacao.mq.consumer.interfaces;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.mq.IConsumer;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;

@Service
public interface ISolicitacaoMensagemCmdCreateConsumer extends IConsumer<SolicitacaoMensagemDto, UUID> {

}