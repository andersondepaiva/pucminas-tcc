package br.com.andersondepaiva.monitoramentobarragens.controller;

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

import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.BarragemDto;

@RestController
@RequestMapping("/barragens")
@Api
public class BarragemController {

	@Autowired
	private IBarragemBusiness barragemBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<BarragemDto> Post(@Valid @RequestBody BarragemDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		BarragemDto retorno = barragemBusiness.save(dto);
		return new ResponseEntity<BarragemDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<BarragemDto> GetById(@PathVariable("id") String id) {
		Optional<BarragemDto> retorno = barragemBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<BarragemDto>(
				retorno.get(), HttpStatus.OK) : new ResponseEntity<>(
				HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<BarragemDto> GetAll(Pageable filtro) {
		return barragemBusiness.getAll(filtro);
	}
}
