package camaratransparente.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.controller.ControllerVereadores;
import camaratransparente.error.exception.EntidadeNaoEncontradaException;
import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.modelo.dto.ModeloVereadorDto;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.repositorio.RepositorioPresencaReuniao;
import camaratransparente.repositorio.RepositorioVereador;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoVereador {

	private final RepositorioVereador repositorioVereador;
	private final RepositorioPresencaReuniao repositorioPresencaReuniao;
	private final WebMvcLinkBuilderFactory linkBuilderFactory;
	
	
	
	@Transactional
	public void salvar(List<ModeloVereador> vereadores) {
		repositorioVereador.saveAll(vereadores);
	}
	
	@Transactional(readOnly = true)
	public List<ModeloVereadorDto> listar() {
		List<ModeloVereador> vereadores = buscarComCusteioComPresenca();
		
		List<ModeloVereadorDto> vereadoresDto = new ArrayList<>();
		for(ModeloVereador vereador : vereadores) {
			String linkFoto = linkBuilderFactory.linkTo(ControllerVereadores.class)
					.slash(vereador.getId())
					.slash("foto")
					.toUri().toString();
			
			vereadoresDto.add(new ModeloVereadorDto(vereador, linkFoto, new EstatisticasPresencasReunioes(vereador.getPresencasReunioes())));
		}
		
		return vereadoresDto;
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
	public byte[] buscarFoto(Long idVereador) {
		if(repositorioVereador.existsById(idVereador)) {
			return repositorioVereador.buscarFotoPorId(idVereador);
		}
		
		throw new EntidadeNaoEncontradaException(idVereador);
	}
	
}
