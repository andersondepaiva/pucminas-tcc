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

import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMinerioBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MinerioDto;

@RestController
@RequestMapping("/minerios")
@Api
public class MinerioController {

	@Autowired
	private IMinerioBusiness minerioBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<MinerioDto> Post(@Valid @RequestBody MinerioDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		MinerioDto retorno = minerioBusiness.save(dto);
		return new ResponseEntity<MinerioDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<MinerioDto> GetById(@PathVariable("id") String id) {
		Optional<MinerioDto> retorno = minerioBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<MinerioDto>(
				retorno.get(), HttpStatus.OK) : new ResponseEntity<>(
				HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<MinerioDto> GetAll(Pageable filtro) {
		return minerioBusiness.getAll(filtro);
	}
}
