package camaratransparente.servico;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import camaratransparente.modelo.VinculacaoDados;
import camaratransparente.modelo.entidade.ModeloScrap;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.repositorio.RepositorioScrap;
import camaratransparente.scrap.CusteioParlamentarMensal;
import camaratransparente.scrap.PresencaMensalIndividual;
import camaratransparente.scrap.ScraperDadosCusteioParlamentar;
import camaratransparente.scrap.ScraperDadosPresencaMensal;
import camaratransparente.scrap.ScraperDadosVereadores;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoScrap {

	private final ServicoVereador servicoVereador;
	private final RepositorioScrap repositorioScrap;
	private final ScraperDadosVereadores scraperDadosVereadores;
	private final ScraperDadosCusteioParlamentar scraperDadosCusteioParlamentar;
	private final ScraperDadosPresencaMensal scraperDadosPresencaMensal;
	private final VinculacaoDados vinculacaoDados;
	

	
	@Transactional(rollbackFor = Exception.class)
	public void realizarScrap() throws IOException, InterruptedException {
		Optional<ModeloScrap> ultimoScrap = repositorioScrap.findTopByOrderByIdDesc();
		Optional<YearMonth> maiorDataCusteioBuscadaUltimaVez = ultimoScrap
				.map(ModeloScrap::getUltimaDataBuscadaCusteio);
		
		Optional<YearMonth> maiorExercicioPresencaBuscadoUltimaVez = ultimoScrap
				.map(ModeloScrap::getUltimoExercicioBuscadoPresenca);
		
		List<CusteioParlamentarMensal> dadosCusteioParlamentar = buscarDadosCusteioParlamentar(maiorDataCusteioBuscadaUltimaVez);
		List<PresencaMensalIndividual> dadosPresenca = buscarDadosPresenca(maiorExercicioPresencaBuscadoUltimaVez);
		
		Optional<YearMonth> maiorDataCusteioBuscadaAgora = dadosCusteioParlamentar.stream()
				.map(CusteioParlamentarMensal::getDataReferencia)
				.max(YearMonth::compareTo);
		
		Optional<YearMonth> maiorDataPresencaBuscadaAgora = dadosPresenca.stream()
				.map(PresencaMensalIndividual::getDataExercicio)
				.max(YearMonth::compareTo);
		
		List<ModeloVereador> vereadores;
		if(ultimoScrap.isPresent()) {
			vereadores = servicoVereador.buscar();
		}else {
			vereadores = scraperDadosVereadores.buscarVereadores();
		}
		
		vereadores = vinculacaoDados.vincular(vereadores, dadosCusteioParlamentar, dadosPresenca);
		servicoVereador.salvar(vereadores);
		
		repositorioScrap.save(new ModeloScrap(maiorDataCusteioBuscadaAgora.orElseGet(maiorDataCusteioBuscadaUltimaVez::get), 
				maiorDataPresencaBuscadaAgora.orElseGet(maiorExercicioPresencaBuscadoUltimaVez::get)));
	}
	
	private List<CusteioParlamentarMensal> buscarDadosCusteioParlamentar(Optional<YearMonth> dataReferenciaUltimoCusteioSalvo) throws IOException {
		if(dataReferenciaUltimoCusteioSalvo.isPresent()) {
			return scraperDadosCusteioParlamentar.buscarHistoricoCusteio(dataReferenciaUltimoCusteioSalvo.get().plusMonths(1)); 
		}
		
		return scraperDadosCusteioParlamentar.buscarHistoricoCusteio(); 
	}
	
	private List<PresencaMensalIndividual> buscarDadosPresenca(Optional<YearMonth> dataUltimoExercicioSalvo) throws IOException {
		if(dataUltimoExercicioSalvo.isPresent()) {
			return scraperDadosPresencaMensal.buscarHistoricoPresenca(dataUltimoExercicioSalvo.get().plusMonths(1));
		}
		
		return scraperDadosPresencaMensal.buscarHistoricoPresenca();
	}
	
}