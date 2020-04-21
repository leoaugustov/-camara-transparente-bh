package camaratransparente.servico;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.modelo.dto.ModeloVereadorDto;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.repositorio.RepositorioPresencaReuniao;
import camaratransparente.repositorio.RepositorioVereador;

@Service
public class ServicoVereador {

	@Autowired
	private RepositorioVereador repositorioVereador;
	
	@Autowired
	private RepositorioPresencaReuniao repositorioPresencaReuniao;
	
	
	
	@Transactional
	public void salvar(List<ModeloVereador> vereadores) {
		repositorioVereador.saveAll(vereadores);
	}
	
	@Transactional
	public List<ModeloVereadorDto> listar() {
		List<ModeloVereador> vereadores = buscarComCusteioComPresenca();
		
		return vereadores.stream()
				.map(ModeloVereadorDto::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public List<ModeloVereador> buscarComCusteioComPresenca() {
		List<ModeloVereador> vereadores = repositorioVereador.buscarTodosComCusteio();
		
		Map<Long, List<ModeloPresencaReuniao>> presencaIndexadaPorIdVereador = repositorioPresencaReuniao.findAll().stream()
				.collect(Collectors.groupingBy(presenca -> presenca.getVereador().getId()));
		
		for(ModeloVereador vereador : vereadores) {
			if(presencaIndexadaPorIdVereador.containsKey(vereador.getId())) {
				presencaIndexadaPorIdVereador.get(vereador.getId())
						.forEach(vereador::adicionarPresencaReuniao);
			}	
		}
		
		return vereadores;
	}
	
}
