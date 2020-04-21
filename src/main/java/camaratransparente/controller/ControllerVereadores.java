package camaratransparente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
		return new ResponseEntity<>(servicoVereador.listar(), HttpStatus.OK);
	}
	
}