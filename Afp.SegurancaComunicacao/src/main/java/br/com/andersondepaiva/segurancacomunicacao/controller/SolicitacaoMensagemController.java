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

import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.ISolicitacaoMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;

@RestController
@RequestMapping("/solicitacoes-mensagem")
@Api
public class SolicitacaoMensagemController {

	@Autowired
	private ISolicitacaoMensagemBusiness solicitacaoMensagemBusiness;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<SolicitacaoMensagemDto> Post(@Valid @RequestBody SolicitacaoMensagemDto dto)
			throws ReflectiveOperationException, NoSuchAlgorithmException {
		SolicitacaoMensagemDto retorno = solicitacaoMensagemBusiness.save(dto);
		return new ResponseEntity<SolicitacaoMensagemDto>(retorno, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<SolicitacaoMensagemDto> GetById(@PathVariable("id") String id) {
		Optional<SolicitacaoMensagemDto> retorno = solicitacaoMensagemBusiness.findById(id);
		return retorno.isPresent() ? new ResponseEntity<SolicitacaoMensagemDto>(retorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public Page<SolicitacaoMensagemDto> GetAll(Pageable filtro) {
		return solicitacaoMensagemBusiness.getAll(filtro);
	}	
}
