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

	@Autowired
	public EquipamentoBusiness(IEquipamentoRepository baseRepository) {
		super(baseRepository);
	}
}
