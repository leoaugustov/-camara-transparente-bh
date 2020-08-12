package camaratransparente.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camaratransparente.servico.ServicoStatusScrap;

@RestController
@RequestMapping("/status-scrap")
public class ControllerStatusScrap {

	@Autowired
	private ServicoStatusScrap servicoStatusScrap;
	
	
	
	@GetMapping
	public ResponseEntity<?> pegarDataUltimaAtualizacao() {
		return ResponseEntity.ok()
				.header(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(15, TimeUnit.MINUTES).getHeaderValue())
				.body(servicoStatusScrap.pegarDataUltimaAtualizacao());
	}
	
}
