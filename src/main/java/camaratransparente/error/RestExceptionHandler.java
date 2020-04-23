package camaratransparente.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import camaratransparente.error.exception.EntidadeNaoEncontradaException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex) {
		return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, null);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		DetalhesErro detalhesErro = new DetalhesErro(status.value(), status.getReasonPhrase(), ex.getMessage());
		return new ResponseEntity<>(detalhesErro, headers, status);
	}
	
}
