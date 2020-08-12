package camaratransparente.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camaratransparente.servico.ServicoVereador;

@RestController
@RequestMapping("/vereadores")
public class ControllerVereadores {

	@Autowired
	private ServicoVereador servicoVereador;
	

	
	@GetMapping
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok()
				.header(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(15, TimeUnit.MINUTES).getHeaderValue())
				.body(servicoVereador.listar());
	}
	
	@GetMapping(path = "/{id}/foto", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<?> pegarFoto(@PathVariable("id") Long id) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(4, TimeUnit.DAYS).getHeaderValue())
				.body(servicoVereador.buscarFoto(id));
	}
	
}