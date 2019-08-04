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

import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMetodoConstrutivoBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MetodoConstrutivoDto;

@RestController
@RequestMapping("/metodos-contrutivos")
@Api
public class MetodoConstrutivoController {

	@Autowired
	private IMetodoConstrutivoBusiness metodoConstrutivoBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<MetodoConstrutivoDto> Post(
			@Valid @RequestBody MetodoConstrutivoDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		MetodoConstrutivoDto retorno = metodoConstrutivoBusiness.save(dto);
		return new ResponseEntity<MetodoConstrutivoDto>(retorno,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<MetodoConstrutivoDto> GetById(
			@PathVariable("id") String id) {
		Optional<MetodoConstrutivoDto> retorno = metodoConstrutivoBusiness
				.findById(id);
		return retorno.isPresent() ? new ResponseEntity<MetodoConstrutivoDto>(
				retorno.get(), HttpStatus.OK) : new ResponseEntity<>(
				HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<MetodoConstrutivoDto> GetAll(Pageable filtro) {
		return metodoConstrutivoBusiness.getAll(filtro);
	}
}
