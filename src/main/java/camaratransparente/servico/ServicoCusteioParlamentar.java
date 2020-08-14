package camaratransparente.servico;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.counting;

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
		List<ModeloVereador> vereadores = repositorioVereador.buscarTodosComCusteio();
		
		Map<String, Double> custeioPorPartido = vereadores.stream()
				.collect(toMap(ModeloVereador::getSiglaPartido, ModeloVereador::getCusteioTotal, Double::sum));
		
		Map<String, Long> quantidadeVereadoresPorPartido = vereadores.stream()
				.collect(groupingBy(ModeloVereador::getSiglaPartido, counting()));
		
		return custeioPorPartido.entrySet().stream().map(entry -> {
			long quantidadeVereadores = quantidadeVereadoresPorPartido.get(entry.getKey());
			
			return new ModeloCusteioPorPartidoDto(entry.getKey(), quantidadeVereadores, entry.getValue());
		}).collect(toList());
	}
	
}
