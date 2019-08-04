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

import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IParametroAlertaBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.ParametroAlertaDto;

@RestController
@RequestMapping("/parametros-alerta")
@Api
public class ParametroAlertaController {

	@Autowired
	private IParametroAlertaBusiness parametroAlertaBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<ParametroAlertaDto> Post(
			@Valid @RequestBody ParametroAlertaDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		ParametroAlertaDto retorno = parametroAlertaBusiness.save(dto);
		return new ResponseEntity<ParametroAlertaDto>(retorno,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<ParametroAlertaDto> GetById(
			@PathVariable("id") String id) {
		Optional<ParametroAlertaDto> retorno = parametroAlertaBusiness
				.findById(id);
		return retorno.isPresent() ? new ResponseEntity<ParametroAlertaDto>(
				retorno.get(), HttpStatus.OK) : new ResponseEntity<>(
				HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<ParametroAlertaDto> GetAll(Pageable filtro) {
		return parametroAlertaBusiness.getAll(filtro);
	}
}
