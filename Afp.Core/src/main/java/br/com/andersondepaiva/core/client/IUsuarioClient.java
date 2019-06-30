package br.com.andersondepaiva.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.andersondepaiva.core.dto.UsuarioDto;

@FeignClient("pessoa")
public interface IUsuarioClient {

	@RequestMapping(method = RequestMethod.GET, value = "/usuario/{id}")
	UsuarioDto getUsuarioById(@PathVariable("id") String id);

}
