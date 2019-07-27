package br.com.andersondepaiva.segurancacomunicacao.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.dto.Comparasion;
import br.com.andersondepaiva.core.dto.ParamDto;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IEscopoBusiness;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.ISolicitacaoMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.ContatoDto;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Contato;
import br.com.andersondepaiva.segurancacomunicacao.model.Mensagem;
import br.com.andersondepaiva.segurancacomunicacao.model.SolicitacaoMensagem;
import br.com.andersondepaiva.segurancacomunicacao.model.Status;
import br.com.andersondepaiva.segurancacomunicacao.mq.producer.interfaces.IMensagemProducer;
import br.com.andersondepaiva.segurancacomunicacao.mq.producer.interfaces.ISolicitacaoMensagemProducer;
import br.com.andersondepaiva.segurancacomunicacao.repository.IContatoRepository;
import br.com.andersondepaiva.segurancacomunicacao.repository.IMensagemRepository;
import br.com.andersondepaiva.segurancacomunicacao.repository.ISolicitacaoMensagemRepository;

@Service
public class SolicitacaoMensagemBusiness extends
		Business<SolicitacaoMensagem, String, SolicitacaoMensagemDto> implements
		ISolicitacaoMensagemBusiness {

	@Autowired
	private ISolicitacaoMensagemProducer solicitacaoMensagemProducer;

	@Autowired
	private IMensagemProducer mensagemProducer;

	@Autowired
	private IContatoRepository contatoRepository;

	@Autowired
	private IEscopoBusiness escopoBusiness;

	@Autowired
	private IMensagemRepository mensagemRepository;

	@Autowired
	public SolicitacaoMensagemBusiness(
			ISolicitacaoMensagemRepository baseRepository) {
		super(baseRepository);
	}

	public SolicitacaoMensagemDto save(SolicitacaoMensagemDto dto)
			throws ReflectiveOperationException {

		for (String escopoId : dto.getEscoposId()) {
			escopoBusiness.getEscopoById(escopoId);
		}

		SolicitacaoMensagem entityModel = modelMapper.map(dto,
				SolicitacaoMensagem.class);

		isValid(entityModel);

		SolicitacaoMensagemDto solicitacaoMensagemDto = modelMapper.map(
				save(entityModel), SolicitacaoMensagemDto.class);

		solicitacaoMensagemProducer.sendMessage(solicitacaoMensagemProducer
				.solicitacaoMensagemExchange().getName(),
				solicitacaoMensagemProducer.getRoutingKeyEventCreate(),
				solicitacaoMensagemDto);

		return solicitacaoMensagemDto;
	}

	public void sendMessages(SolicitacaoMensagemDto dto) {

		SolicitacaoMensagem solicitacaoMensagem = modelMapper.map(dto,
				SolicitacaoMensagem.class);

		for (String escopoId : solicitacaoMensagem.getEscoposId()) {

			List<Contato> contatos = contatoRepository
					.findAllByEscopoIdAndExcluidoFalse(escopoId);

			for (Contato contato : contatos) {
				MensagemDto mensagem = MensagemDto.builder()
						.solicitacaoMensagem(solicitacaoMensagem)
						.contato(modelMapper.map(contato, ContatoDto.class))
						.build();

				mensagemProducer.sendMessage(mensagemProducer
						.mensagemExchange().getName(), mensagemProducer
						.getRoutingKeyCmdSend(), mensagem);
			}
		}

	}

	private void isValid(SolicitacaoMensagem entityModel) {
		checkMessageProcessingRequest(entityModel);
	}

	private void checkMessageProcessingRequest(SolicitacaoMensagem model) {

		if (mongoTemplate.exists(
				buildQueryByStatusAndEscoposId(Status.EM_PROCESSAMENTO,
						model.getEscoposId()), Mensagem.class))
			throwExceptionSolicitacaoJaExistente();

		if (mongoTemplate.exists(
				buildQueryByStatusAndEscoposId(Status.PENDENTE,
						model.getEscoposId()), Mensagem.class))
			throwExceptionSolicitacaoJaExistente();
	}

	private void throwExceptionSolicitacaoJaExistente() {
		throw new BusinessException(
				"Já exite uma solicitação de mensagem em processamento com esses parâmetros");
	}

	private Query buildQueryByStatusAndEscoposId(Status status,
			List<String> escopoIds) {
		List<ParamDto> params = new ArrayList<ParamDto>();

		params.add(ParamDto.builder().comparasion(Comparasion.EQUALS)
				.field("status").value(status).build());

		Query query = buildQuery(params);
		Criteria criteria = Criteria.where("solicitacaoMensagem.escoposId");
		query.addCriteria(criteria.all(escopoIds));
		
		return query;
	}
}
