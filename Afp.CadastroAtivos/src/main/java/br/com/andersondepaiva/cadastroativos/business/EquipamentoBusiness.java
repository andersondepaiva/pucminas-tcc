package br.com.andersondepaiva.cadastroativos.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.cadastroativos.business.interfaces.IEquipamentoBusiness;
import br.com.andersondepaiva.cadastroativos.dto.EquipamentoDto;
import br.com.andersondepaiva.cadastroativos.model.Equipamento;
import br.com.andersondepaiva.cadastroativos.repository.IEquipamentoRepository;
import br.com.andersondepaiva.core.business.Business;

@Service
public class EquipamentoBusiness extends Business<Equipamento, String, EquipamentoDto> implements IEquipamentoBusiness {

	/*@Autowired
	private IProfissaoProducer profissaoProducer;*/

	@Autowired
	public EquipamentoBusiness(IEquipamentoRepository baseRepository) {
		super(baseRepository);
	}

	/*@Override
	public EquipamentoDto save(EquipamentoDto dto) throws ReflectiveOperationException {
		isValid(dto);

		EquipamentoDto dtoPersisted = super.save(dto);

		/*profissaoProducer.sendMessage(profissaoProducer.profissaoExchange().getName(),
				profissaoProducer.getRoutingKeyEventCreate(), dtoPersisted);

		return dtoPersisted;
	}*/
}
