package br.com.andersondepaiva.core.infra.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonService {

	@Autowired
	protected ObjectMapper objectMapper;
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
