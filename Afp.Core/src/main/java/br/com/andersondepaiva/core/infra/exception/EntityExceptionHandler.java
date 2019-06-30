package br.com.andersondepaiva.core.infra.exception;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.andersondepaiva.core.infra.exception.model.BusinessException;
import br.com.andersondepaiva.core.infra.exception.model.ErrorDto;
import br.com.andersondepaiva.core.infra.exception.model.ErrorResponse;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EntityExceptionHandler {

	@ExceptionHandler(value = { BusinessException.class })
	public ResponseEntity<ErrorResponse> handleAnyException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "application/json");
		return new ResponseEntity<ErrorResponse>(
				ErrorResponse.builder().timestamp(LocalDateTime.now()).message("Bad Request")
						.errors(Arrays.asList(ErrorDto.builder().defaultMessage(ex.getMessage()).build()))
						.details(request.getDescription(false)).build(),
				headers, HttpStatus.BAD_REQUEST);
	}

}
