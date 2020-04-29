package camaratransparente.servico;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.error.exception.EntidadeNaoEncontradaException;
import camaratransparente.modelo.EstatisticasPresencasReunioes;
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
	
	@Transactional(readOnly = true)
	public List<ModeloVereadorDto> listar() {
		List<ModeloVereador> vereadores = buscarComCusteioComPresenca();
		
		return vereadores.stream()
				.map(vereador -> new ModeloVereadorDto(vereador, new EstatisticasPresencasReunioes(vereador.getPresencasReunioes())))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
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
	
	@Transactional(readOnly = true)
	public Map<String, String> buscarFoto(Long idVereador) {
		if(repositorioVereador.existsById(idVereador)) {
			byte[] foto = repositorioVereador.buscarFotoPorId(idVereador);
			return Collections.singletonMap("foto", Base64.getEncoder().encodeToString(foto));
		}
		
		throw new EntidadeNaoEncontradaException(idVereador);
	}
	
}
