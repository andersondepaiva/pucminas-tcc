package br.com.andersondepaiva.monitoramentobarragens.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.andersondepaiva.monitoramentobarragens.dto.EscopoDto;

@FeignClient("segurancacomunicacao")
public interface IEscopoClient {

	@RequestMapping(method = RequestMethod.GET, value = "/escopos")
	List<EscopoDto> getEscoposById(@RequestParam("ids") List<String> ids);

}