package camaratransparente.servico;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.modelo.entidade.ModeloScrap;
import camaratransparente.repositorio.RepositorioScrap;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoStatusScrap {

	private final RepositorioScrap repositorioScrap;
	
	
	
	@Transactional(readOnly = true)
	@Cacheable("status-scrap")
	public Map<String, LocalDateTime> pegarDataUltimaAtualizacao() {
		LocalDateTime dataUltimaAtualizacao = repositorioScrap.findTopByOrderByIdDesc()
				.map(ModeloScrap::getDataExecucao)
				.orElse(null);
		
		return Collections.singletonMap("dataUltimaAtualizacao", dataUltimaAtualizacao);
	}
	
}
