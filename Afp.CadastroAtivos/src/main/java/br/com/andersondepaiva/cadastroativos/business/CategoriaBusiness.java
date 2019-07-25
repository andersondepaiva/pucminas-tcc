package br.com.andersondepaiva.cadastroativos.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.cadastroativos.business.interfaces.ICategoriaBusiness;
import br.com.andersondepaiva.cadastroativos.dto.CategoriaDto;
import br.com.andersondepaiva.cadastroativos.model.Categoria;
import br.com.andersondepaiva.cadastroativos.repository.ICategoriaRepository;
import br.com.andersondepaiva.core.business.Business;

@Service
public class CategoriaBusiness extends Business<Categoria, String, CategoriaDto> implements ICategoriaBusiness {

	@Autowired
	public CategoriaBusiness(ICategoriaRepository baseRepository) {
		super(baseRepository);
	}
}
