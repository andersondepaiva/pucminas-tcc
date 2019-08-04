package br.com.andersondepaiva.monitoramentobarragens.business;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.monitoramentobarragens.business.interfaces.IParametroAlertaBusiness;
import br.com.andersondepaiva.monitoramentobarragens.client.IEscopoClient;
import br.com.andersondepaiva.monitoramentobarragens.dto.EscopoDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.MonitoramentoBarragemDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.ParametroAlertaDto;
import br.com.andersondepaiva.monitoramentobarragens.dto.SolicitacaoMensagemDto;
import br.com.andersondepaiva.monitoramentobarragens.model.Parametro;
import br.com.andersondepaiva.monitoramentobarragens.model.ParametroAlerta;
import br.com.andersondepaiva.monitoramentobarragens.mq.producer.interfaces.ISolicitacaoMensagemCmdCreateProducer;
import br.com.andersondepaiva.monitoramentobarragens.repository.IBarragemRepository;
import br.com.andersondepaiva.monitoramentobarragens.repository.IParametroAlertaRepository;

@Service
public class ParametroAlertaBusiness extends
		Business<ParametroAlerta, String, ParametroAlertaDto> implements
		IParametroAlertaBusiness {

	@Autowired
	private IBarragemRepository barragemRepository;

	@Autowired
	private IEscopoClient escopoClient;

	@Autowired
	private ISolicitacaoMensagemCmdCreateProducer solicitacaoMensagemProducer;

	@Autowired
	public ParametroAlertaBusiness(IParametroAlertaRepository baseRepository) {
		super(baseRepository);
	}

	@Override
	public ParametroAlertaDto save(ParametroAlertaDto dto)
			throws ReflectiveOperationException {

		if (dto.getBarragensId() != null && !dto.getBarragensId().isEmpty())
			for (String barragemId : dto.getBarragensId()) {
				if (!barragemRepository.existsById(barragemId))
					throw new BusinessException(String.format(
							"Barragem %s não encontrada", barragemId));
			}

		List<EscopoDto> escopos = escopoClient.getEscoposById(dto
				.getEscoposId());

		if (escopos == null || escopos.isEmpty())
			throw new BusinessException("Escopos não encontrados");

		ParametroAlerta entity = modelMapper.map(dto, ParametroAlerta.class);

		List<String> escoposId = new ArrayList<String>();

		for (EscopoDto escopoDto : escopos)
			escoposId.add(escopoDto.getId());

		entity.setEscoposId(escoposId);

		return modelMapper.map(baseRepository.save(entity),
				ParametroAlertaDto.class);
	}

	public void verifyParametrosAlerta(
			MonitoramentoBarragemDto monitoramentoBarragemDto) throws Exception {

		try {
			List<ParametroAlerta> parametrosAlerta = mongoTemplate.find(
					buildQueryBarragensIdIsEmpty(), ParametroAlerta.class);

			List<String> barragensId = new ArrayList<String>();
			barragensId.add(monitoramentoBarragemDto.getBarragemId());

			parametrosAlerta.addAll(mongoTemplate
					.find(buildQueryByBarragensId(barragensId),
							ParametroAlerta.class));

			for (ParametroAlerta parametroAlerta : parametrosAlerta) {

				List<String> mensagensAlerta = new ArrayList<String>();

				for (Parametro parametro : parametroAlerta.getParametros()) {

					Object indicadorComparacao = new PropertyDescriptor(
							parametro.getIndicador(),
							MonitoramentoBarragemDto.class).getReadMethod()
							.invoke(monitoramentoBarragemDto);

					BigDecimal valorComparacao = (BigDecimal) indicadorComparacao;

					if (valorComparacao.compareTo(parametro.getMenorQue()) == -1
							|| valorComparacao.compareTo(parametro
									.getMaiorQue()) == 1) {
						mensagensAlerta.add(parametro.getMensagemAlerta());
					}
				}

				String mensagemAlerta = null;

				if (mensagensAlerta.isEmpty())
					mensagemAlerta = parametroAlerta.getMensagemAlerta();
				else
					mensagemAlerta = StringUtils.join(mensagensAlerta, ", ");

				SolicitacaoMensagemDto solicitacaoMensagemDto = SolicitacaoMensagemDto
						.builder()
						.escoposId(parametroAlerta.getEscoposId())
						.motivo(String
								.format("Alerta devido as condições dos parâmetros de alerta %s ",
										parametroAlerta.getDescricao()))
						.mensagem(mensagemAlerta).build();

				solicitacaoMensagemProducer.sendMessage(
						solicitacaoMensagemProducer
								.solicitacaoMensagemExchange().getName(),
						solicitacaoMensagemProducer.getRoutingKeyCmdCreate(),
						solicitacaoMensagemDto);
			}

		} catch (IllegalArgumentException | IllegalAccessException
				| SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	private Query buildQueryByBarragensId(List<String> barragensId) {

		Query query = new Query();
		Criteria criteria = Criteria.where("barragensId");
		query.addCriteria(criteria.all(barragensId));

		return query;
	}

	private Query buildQueryBarragensIdIsEmpty() {

		Query query = new Query();
		Criteria criteria = Criteria.where("barragensId").size(0);
		query.addCriteria(criteria);

		return query;
	}
}
