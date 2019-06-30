
package br.com.andersondepaiva.core.infra.controller;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.andersondepaiva.core.infra.utils.JsonService;

@ControllerAdvice
public class RequestInterceptor extends RequestBodyAdviceAdapter {

	@Autowired
	private RequestContext requestContext;

	@Autowired
	private JsonService jsonService;

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {

		if (parameter.getMethod() != null) {
			requestContext.setMethod(parameter.getMethod().getName().toUpperCase());
		}

		if (isMethodWithBody(parameter)) {
			try {
				requestContext.setBody(jsonService.getObjectMapper().writeValueAsString(body));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return body;
	}

	private boolean isMethodWithBody(MethodParameter methodParameter) {
		String method = methodParameter.getMethod().getName();
		if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE")
				|| method.equalsIgnoreCase("PATCH")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

}
