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

import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IEscopoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.EscopoDto;

@RestController
@RequestMapping("/escopos")
@Api
public class EscopoController {

	@Autowired
	private IEscopoBusiness escopoBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<EscopoDto> Post(@Valid @RequestBody EscopoDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		EscopoDto retorno = escopoBusiness.save(dto);
		return new ResponseEntity<EscopoDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<EscopoDto> GetById(@PathVariable("id") String id) {
		Optional<EscopoDto> retorno = escopoBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<EscopoDto>(retorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<EscopoDto> GetAll(Pageable filtro) {
		return escopoBusiness.getAll(filtro);
	}	
}
