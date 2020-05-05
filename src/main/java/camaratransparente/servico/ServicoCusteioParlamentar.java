package camaratransparente.servico;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.modelo.dto.ModeloCusteioPorPartidoDto;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.repositorio.RepositorioVereador;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoCusteioParlamentar {

	private final RepositorioVereador repositorioVereador;

	
	
	@Transactional(readOnly = true)
	public List<ModeloCusteioPorPartidoDto> listarPorPartido() {
		Map<String, Double> custeioPorPartido = repositorioVereador.buscarTodosComCusteio().stream()
				.collect(toMap(ModeloVereador::getPartido, ModeloVereador::getCusteioTotal, Double::sum));
		
		return custeioPorPartido.entrySet().stream().map(entry -> {
			String siglaPartido = entry.getKey().split("-")[0];
			
			return new ModeloCusteioPorPartidoDto(siglaPartido.trim(), entry.getValue());
		}).collect(toList());
	}
	
}
