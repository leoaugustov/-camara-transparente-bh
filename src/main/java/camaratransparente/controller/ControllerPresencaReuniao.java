package camaratransparente.controller;

import java.time.YearMonth;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.servico.ServicoPresencaReuniao;

@RestController
@RequestMapping("/presencas-reunicoes")
public class ControllerPresencaReuniao {

	@Autowired
	private ServicoPresencaReuniao servicoPresencaReuniao;
	
	
	
	@GetMapping("/estatisticas-mensais/{idVereador}")
	public TreeMap<YearMonth, EstatisticasPresencasReunioes> listar(@PathVariable("idVereador") Long idVereador) {
		return servicoPresencaReuniao.listarEstatisticasPresencasReunioesMensalmente(idVereador);
	}
	
}
