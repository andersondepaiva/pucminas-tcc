package br.com.andersondepaiva.segurancacomunicacao.business;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.andersondepaiva.core.business.Business;
import br.com.andersondepaiva.segurancacomunicacao.business.interfaces.IMensagemBusiness;
import br.com.andersondepaiva.segurancacomunicacao.dto.MensagemDto;
import br.com.andersondepaiva.segurancacomunicacao.model.Mensagem;
import br.com.andersondepaiva.segurancacomunicacao.model.Status;
import br.com.andersondepaiva.segurancacomunicacao.repository.IMensagemRepository;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

@Service
public class MensagemBusiness extends Business<Mensagem, String, MensagemDto>
		implements IMensagemBusiness {

	@Value("${app.sms.api-key}")
	private String apiKeySMS;

	@Value("${app.sms.api-secret}")
	private String apiSecret;

	@Value("${app.sms.from-title}")
	private String fromSms;

	@Value("${app.sms.is-send:false}")
	private boolean isSend;

	@Autowired
	public MensagemBusiness(IMensagemRepository baseRepository) {
		super(baseRepository);
	}

	@Override
	public void send(MensagemDto message) throws IOException,
			NexmoClientException {

		Mensagem mensagemEntity = modelMapper.map(message, Mensagem.class);

		mensagemEntity = getMessageIfExists(mensagemEntity);

		if (mensagemEntity.getStatus() == Status.PROCESSADO)
			return;

		mensagemEntity.setStatus(Status.EM_PROCESSAMENTO);

		mensagemEntity = save(mensagemEntity);

		sendSMS(mensagemEntity);

		updateStatus(mensagemEntity.getId(), Status.PROCESSADO);

	}

	private void sendSMS(Mensagem mensagem) throws IOException,
			NexmoClientException {
		try {

			NexmoClient client = new NexmoClient.Builder().apiKey("5831ea79")
					.apiSecret("REOduqS9o658iixe").build();

			String messageText = mensagem.getSolicitacaoMensagem()
					.getMensagem();
			TextMessage textMessage = new TextMessage(fromSms, mensagem
					.getContato().getNumeroCompleto(), messageText);

			if (isSend) {
				SmsSubmissionResponse response = client.getSmsClient()
						.submitMessage(textMessage);

				for (SmsSubmissionResponseMessage responseMessage : response
						.getMessages()) {
					System.out.println(responseMessage);
				}
			}
		} catch (IOException | NexmoClientException ex) {
			updateStatus(mensagem.getId(), Status.FALHA);
			throw ex;
		}
	}

	private Mensagem getMessageIfExists(Mensagem mensagem) {
		Optional<Mensagem> optionalMensagem = baseRepository
				.findOne(buildModelCriteria(mensagem));

		if (!optionalMensagem.isPresent())
			return mensagem;

		return optionalMensagem.get();
	}

	private void updateStatus(String mensagemId, Status status) {
		Update updateStatusMensagem = new Update();
		updateStatusMensagem.set("status", status);
		executeAtomicUpdate(updateStatusMensagem, mensagemId);
	}

	private Example<Mensagem> buildModelCriteria(Mensagem model) {
		return Example.of(Mensagem.builder()
				.solicitacaoMensagem(model.getSolicitacaoMensagem())
				.contato(model.getContato()).build());
	}
}
