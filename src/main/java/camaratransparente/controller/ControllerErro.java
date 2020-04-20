package camaratransparente.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camaratransparente.error.DetalhesErro;

@RestController
public class ControllerErro implements ErrorController {

	@RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DetalhesErro> tratarErro(HttpServletResponse resposta) {
		HttpStatus status = HttpStatus.valueOf(resposta.getStatus());
		
		DetalhesErro detalhesErro = new DetalhesErro(status);
		return new ResponseEntity<>(detalhesErro, status);
	}
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}
