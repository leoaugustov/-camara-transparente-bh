package camaratransparente.servico;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.error.exception.EntidadeNaoEncontradaException;
import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.repositorio.RepositorioPresencaReuniao;
import camaratransparente.repositorio.RepositorioVereador;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoPresencaReuniao {

	private final RepositorioVereador repositorioVereador;
	private final RepositorioPresencaReuniao repositorioPresencaReuniao;
	
	
	
	@Transactional(readOnly = true)
	public TreeMap<YearMonth, EstatisticasPresencasReunioes> listarEstatisticasPresencasReunioesMensalmente(Long idVereador) {
		if(repositorioVereador.existsById(idVereador)) {
			Map<YearMonth, List<ModeloPresencaReuniao>> presencasReunioes = repositorioPresencaReuniao.findByVereadorId(idVereador).stream()
					.collect(groupingBy(presencaReuniao -> presencaReuniao.getDataExercicio().get()));
			
			return presencasReunioes.entrySet().stream()
					.collect(toMap(Entry::getKey, entry -> new EstatisticasPresencasReunioes(entry.getValue()), (a, b) -> b, TreeMap::new));
		}
		
		throw new EntidadeNaoEncontradaException(idVereador);
	}
	
}
