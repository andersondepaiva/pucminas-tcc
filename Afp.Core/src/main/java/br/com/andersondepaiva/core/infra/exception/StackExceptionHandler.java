package br.com.andersondepaiva.core.infra.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.andersondepaiva.core.infra.controller.RequestContext;
import br.com.andersondepaiva.core.infra.exception.model.ExceptionDto;
import br.com.andersondepaiva.core.infra.exception.producer.interfaces.IExceptionProducer;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class StackExceptionHandler {

	private final String MESSAGE = "Ocorreu um erro inesperado";

	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private IExceptionProducer exceptionProducer;

	@Autowired
	private RequestContext requestContext;

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ExceptionDto> handleAnyException(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Throwable arff) {

		ExceptionDto dto = ExceptionDto.builder().timestamp(LocalDateTime.now()).exception(new Exception(arff))
				.api(applicationName).message(MESSAGE).body(requestContext.getBody()).uri(request.getRequestURI())
				.method(requestContext.getMethod() == null || requestContext.getMethod().isEmpty() ? request.getMethod()
						: requestContext.getMethod())
				.build();

		exceptionProducer.sendMessage(exceptionProducer.exceptionExchange().getName(), "exception.event.throw", dto);
		
		return new ResponseEntity<ExceptionDto>(
				ExceptionDto.builder().timestamp(dto.getTimestamp()).message(MESSAGE).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
