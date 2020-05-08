package camaratransparente.servico;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Service;

import camaratransparente.modelo.entidade.ModeloScrap;
import camaratransparente.repositorio.RepositorioScrap;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoStatusScrap {

	private final RepositorioScrap repositorioScrap;
	
	
	
	public Map<String, LocalDateTime> pegarDataUltimaAtualizacao() {
		LocalDateTime dataUltimaAtualizacao = repositorioScrap.findTopByOrderByIdDesc()
				.map(ModeloScrap::getDataExecucao)
				.orElse(null);
		
		return Collections.singletonMap("dataUltimaAtualizacao", dataUltimaAtualizacao);
	}
	
}
