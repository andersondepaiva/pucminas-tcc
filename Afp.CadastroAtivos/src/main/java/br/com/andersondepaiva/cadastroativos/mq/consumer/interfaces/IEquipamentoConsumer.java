package br.com.andersondepaiva.cadastroativos.mq.consumer.interfaces;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.andersondepaiva.cadastroativos.dto.EquipamentoDto;
import br.com.andersondepaiva.core.mq.IConsumer;

@Service
public interface IEquipamentoConsumer extends IConsumer<EquipamentoDto, UUID> {

}
