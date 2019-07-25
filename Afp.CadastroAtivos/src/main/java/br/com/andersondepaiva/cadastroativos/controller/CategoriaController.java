package br.com.andersondepaiva.cadastroativos.controller;

import io.swagger.annotations.Api;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.andersondepaiva.cadastroativos.business.interfaces.ICategoriaBusiness;
import br.com.andersondepaiva.cadastroativos.dto.CategoriaDto;

@RestController
@RequestMapping("/categorias")
@Api
public class CategoriaController {

	@Autowired
	private ICategoriaBusiness categoriaBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<CategoriaDto> Post(@Valid @RequestBody CategoriaDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		CategoriaDto retorno = categoriaBusiness.save(dto);
		return new ResponseEntity<CategoriaDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<CategoriaDto> GetById(@PathVariable("id") String id) {
		Optional<CategoriaDto> retorno = categoriaBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<CategoriaDto>(retorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<CategoriaDto> GetAll(Pageable filtro) {
		return categoriaBusiness.getAll(filtro);
	}
	
	
}
