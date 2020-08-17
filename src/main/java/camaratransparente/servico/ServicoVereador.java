package camaratransparente.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.controller.ControllerVereadores;
import camaratransparente.error.exception.EntidadeNaoEncontradaException;
import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.modelo.dto.ModeloVereadorDto;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.repositorio.RepositorioVereador;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoVereador {

	private final RepositorioVereador repositorioVereador;
	private final WebMvcLinkBuilderFactory linkBuilderFactory;
	
	
	
	@Transactional
	public void salvar(List<ModeloVereador> vereadores) {
		repositorioVereador.saveAll(vereadores);
	}
	
	@Transactional(readOnly = true)
	@Cacheable("vereadores")
	public List<ModeloVereadorDto> listar() {
		List<ModeloVereador> vereadores = repositorioVereador.buscarTodosComPresencasReunioes();
		
		List<ModeloVereadorDto> vereadoresDto = new ArrayList<>();
		for(ModeloVereador vereador : vereadores) {
			String linkFoto = linkBuilderFactory.linkTo(ControllerVereadores.class)
					.slash(vereador.getUuid())
					.slash("foto")
					.toUri().toString();
			
			vereadoresDto.add(new ModeloVereadorDto(vereador, linkFoto, new EstatisticasPresencasReunioes(vereador.getPresencasReunioes())));
		}
		
		return vereadoresDto;
	}
	
	@Transactional(readOnly = true)
	public List<ModeloVereador> buscar() {
		return repositorioVereador.findAll();
	}
	
	@Transactional(readOnly = true)
	@Cacheable("foto-vereador")
	public byte[] buscarFoto(UUID idVereador) {
		if(repositorioVereador.existsByUuid(idVereador)) {
			return repositorioVereador.buscarFotoPorUuid(idVereador);
		}
		
		throw new EntidadeNaoEncontradaException(idVereador);
	}
	
}
