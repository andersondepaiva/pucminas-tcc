package br.com.andersondepaiva.cadastroativos.controller;

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

import br.com.andersondepaiva.cadastroativos.business.interfaces.IEquipamentoBusiness;
import br.com.andersondepaiva.cadastroativos.dto.EquipamentoDto;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/equipamentos")
@Api
public class EquipamentoController {

	@Autowired
	private IEquipamentoBusiness equipamentoBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<EquipamentoDto> Post(@Valid @RequestBody EquipamentoDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		EquipamentoDto retorno = equipamentoBusiness.save(dto);
		return new ResponseEntity<EquipamentoDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<EquipamentoDto> GetById(@PathVariable("id") String id) {
		Optional<EquipamentoDto> retorno = equipamentoBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<EquipamentoDto>(retorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<EquipamentoDto> GetAll(Pageable filtro) {
		return equipamentoBusiness.getAll(filtro);
	}
	
	
}
