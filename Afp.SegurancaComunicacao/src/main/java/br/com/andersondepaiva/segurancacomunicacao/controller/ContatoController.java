package br.com.andersondepaiva.segurancacomunicacao.controller;

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

import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IContatoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.ContatoDto;

@RestController
@RequestMapping("/contatos")
@Api
public class ContatoController {

	@Autowired
	private IContatoBusiness contatoBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<ContatoDto> Post(@Valid @RequestBody ContatoDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		ContatoDto retorno = contatoBusiness.save(dto);
		return new ResponseEntity<ContatoDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<ContatoDto> GetById(@PathVariable("id") String id) {
		Optional<ContatoDto> retorno = contatoBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<ContatoDto>(retorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<ContatoDto> GetAll(Pageable filtro) {
		return contatoBusiness.getAll(filtro);
	}	
}
