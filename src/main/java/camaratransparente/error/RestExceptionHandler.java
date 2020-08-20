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
import camaratransparente.error.exception.QuantidadeRequisicoesEsgotadaException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(QuantidadeRequisicoesEsgotadaException.class)
	protected ResponseEntity<?> handleQuantidadeRequisicoesEsgotadaException(QuantidadeRequisicoesEsgotadaException ex) {
		HttpStatus status = HttpStatus.TOO_MANY_REQUESTS;
		
		HttpHeaders cabecalhos = new HttpHeaders();
		cabecalhos.add("X-Rate-Limit-Retry-After-Seconds", String.valueOf(ex.getSegundosAteProximaTentativa()));
		
		DetalhesErro detalhesErro = criarDetalhesErro(status, ex);
		return criarResponseEntity(detalhesErro, cabecalhos, status);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	protected ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		DetalhesErro detalhesErro = criarDetalhesErro(status, ex);
		return criarResponseEntity(detalhesErro, null, status);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Erro interno.", ex);
		DetalhesErro detalhesErro = criarDetalhesErro(status, ex);
		return criarResponseEntity(detalhesErro, headers, status);
	}
	
	
	
	private DetalhesErro criarDetalhesErro(HttpStatus status, Exception ex) {
		return new DetalhesErro(status.value(), status.getReasonPhrase(), ex.getMessage());
	}
	
	private ResponseEntity<Object> criarResponseEntity(DetalhesErro detalhesErro, HttpHeaders headers, HttpStatus status) {
		return new ResponseEntity<>(detalhesErro, headers, status);
	}
	
}
