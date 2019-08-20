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

import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IMonitoramentoBarragemBusiness;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.RelatorioMonitoramentoBarragemDto;

@RestController
@RequestMapping("/monitoramento-barragens")
@Api
public class MonitoramentoBarragemController {

	@Autowired
	private IMonitoramentoBarragemBusiness monitoramentoBarragemBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<MonitoramentoBarragemDto> Post(
			@Valid @RequestBody MonitoramentoBarragemDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		MonitoramentoBarragemDto retorno = monitoramentoBarragemBusiness
				.save(dto);
		return new ResponseEntity<MonitoramentoBarragemDto>(retorno,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<MonitoramentoBarragemDto> GetById(
			@PathVariable("id") String id) {
		Optional<MonitoramentoBarragemDto> retorno = monitoramentoBarragemBusiness
				.findById(id);
		return retorno.isPresent() ? new ResponseEntity<MonitoramentoBarragemDto>(
				retorno.get(), HttpStatus.OK) : new ResponseEntity<>(
				HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<MonitoramentoBarragemDto> GetAll(Pageable filtro) {
		return monitoramentoBarragemBusiness.getAll(filtro);
	}

	@RequestMapping(value = "/barragem/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public RelatorioMonitoramentoBarragemDto GetAllByBarragem(
			@PathVariable("id") String id) {
		return monitoramentoBarragemBusiness.getAllByBarragem(id);
	}
}
