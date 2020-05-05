package camaratransparente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camaratransparente.servico.ServicoCusteioParlamentar;

@RestController
@RequestMapping("/custeio-parlamentar")
public class ControllerCusteioParlamentar {

	@Autowired
	private ServicoCusteioParlamentar servicoCusteioParlamentar;
	
	
	
	@GetMapping("/por-partido")
	public ResponseEntity<?> listarPorPartido() {
		return new ResponseEntity<>(servicoCusteioParlamentar.listarPorPartido(), HttpStatus.OK);
	}
	
}