package camaratransparente.scrap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import camaratransparente.modelo.LegendaPresencaReuniao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScraperDadosPresencaMensal {

	private static final String URL_PESQUISA = "https://www.cmbh.mg.gov.br/transparencia/vereadores/presenca-mensal-consolidada";
	private static final YearMonth DATA_EXERCICIO_MAIS_ANTIGO_BUSCADO = YearMonth.of(2017, Month.FEBRUARY);
	
	private final RestTemplate httpClient;
	
	
	
	/**
	 * Realiza a busca do histórico de presença mensal consolidada de todos os vereadores em todos os exercícios disponíveis.
	 * @param dataExercicioMaisAntigoBuscado - a data do exercício mais antigo buscado
	 * @return uma lista contendo os dados mensais individuais encontrados
	 * @throws IOException caso aconteça algum erro durante o processo
	 */
	public List<PresencaMensalIndividual> buscarHistoricoPresenca() throws IOException {
		return buscarHistoricoPresenca(DATA_EXERCICIO_MAIS_ANTIGO_BUSCADO);
	}
	
	/**
	 * Realiza a busca do histórico de presença mensal consolidada de todos os vereadores na 
	 * data do exercício informada e em todos os exercícios posteriores.
	 * @param dataExercicioMaisAntigoBuscado - a data do exercício mais antigo buscado
	 * @return uma lista contendo os dados mensais individuais encontrados
	 * @throws IOException caso aconteça algum erro durante o processo
	 */
	public List<PresencaMensalIndividual> buscarHistoricoPresenca(YearMonth dataExercicioMaisAntigoBuscado) throws IOException {
		if(dataExercicioMaisAntigoBuscado.isBefore(DATA_EXERCICIO_MAIS_ANTIGO_BUSCADO)) {
			dataExercicioMaisAntigoBuscado = DATA_EXERCICIO_MAIS_ANTIGO_BUSCADO;
		}
		
		Document pagina = pesquisar();
		final YearMonth dataExercicioMaisNovo = extrairDataExercicioSelecionada(pagina);
		
		if(dataExercicioMaisAntigoBuscado.isAfter(dataExercicioMaisNovo)) {
			return Collections.emptyList();
		}
		
		List<PresencaMensalIndividual> dados = new ArrayList<>();
		dados.addAll(buscarDados(pagina, dataExercicioMaisNovo));
		
		YearMonth dataExercicio = gerarDataProximoExercicio(dataExercicioMaisNovo);
		while(dataExercicio.isAfter(dataExercicioMaisAntigoBuscado) || dataExercicio.equals(dataExercicioMaisAntigoBuscado)) {
			pagina = pesquisar(dataExercicio);
			dados.addAll(buscarDados(pagina, dataExercicio));
			
			dataExercicio = gerarDataProximoExercicio(dataExercicio);
		}
		
		return dados;
	}

	private List<PresencaMensalIndividual> buscarDados(Document pagina, YearMonth dataExercicio) {
		Elements tabelasPresenca = pagina.select("h3 + .table-responsive");
		
		List<PresencaMensalIndividual> dados = new ArrayList<>();
		
		for(Element tabela : tabelasPresenca) {
			String nomeVereador = ((Element) tabela.previousElementSibling()).text();
			PresencaMensalIndividual presencaMensal = new PresencaMensalIndividual(nomeVereador, dataExercicio);
			
			Elements colunas = tabela.select(".cab_th");
			Elements linha = tabela.select(".td_pad");
			
			for(Element coluna : colunas) {
				String statusPresencao = linha.get(coluna.elementSiblingIndex()).text();
				
				if(LegendaPresencaReuniao.isLegendaValida(statusPresencao)) {
					int diaReuniao = Integer.parseInt(limparTextoDiaReuniao(coluna.text()));
					presencaMensal.adicionarPresenca(diaReuniao, statusPresencao);
				}	
			}
			
			dados.add(presencaMensal);
		}
		
		return dados;
	}
	
	/**
	 * Extrai a data do exercício selecionada da página informada.
	 */
	private YearMonth extrairDataExercicioSelecionada(Document pagina) {
		int anoExercicio = pagina.select("#ano option").stream()
				.map(option -> option.attr("value"))
				.map(Integer::parseInt)
				.max(Integer::compareTo).get();
		
		Month mesExercicio = pagina.select("#mes option").stream()
				.map(option -> option.attr("value"))
				.map(Integer::parseInt)
				.max(Integer::compareTo)
				.map(Month::of).get();
		
		return YearMonth.of(anoExercicio, mesExercicio);
	}
	
	/**
	 * Realiza a pesquisa da página sem passar nenhuma referência a qualquer exercício. Supostamente a página retornada 
	 * vai conter o dados do exercício mais novo.
	 * @return a página retornada
	 */
	public Document pesquisar() {
		ResponseEntity<String> resposta = httpClient.exchange(URL_PESQUISA, HttpMethod.GET, null, String.class);
		return Jsoup.parse(resposta.getBody());
	}
	
	/**
	 * Realiza a pesquisa da página contendo os dados do exerício referente a data informada.
	 * @param dataExercicio - a data do exercício buscado
	 * @return a página retornada
	 */
	public Document pesquisar(YearMonth dataExercicio) {
		int anoExercicio = dataExercicio.getYear();
		String mesExercicio = String.valueOf(dataExercicio.getMonthValue());
		if(dataExercicio.getMonthValue() < 10) {
			mesExercicio = "0" +mesExercicio;
		}
		
		String body = String.format("ano=%s&mes=%s", anoExercicio, mesExercicio);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setHost(new InetSocketAddress("www.cmbh.mg.gov.br", 0));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setContentLength(15);
		
		HttpEntity<String> requisicao = new HttpEntity<String>(body, headers);
		String resposta = httpClient.postForObject(URL_PESQUISA, requisicao, String.class);
		return Jsoup.parse(resposta);
	}
	
	/**
	 * Gera a data do próximo exercício que deve ser lido.
	 * @return a data do exercício que será lido em seguida ao exercício referente a data informada
	 */
	private YearMonth gerarDataProximoExercicio(YearMonth dataExercicioAtual) {
		YearMonth proximaDataExercicio = dataExercicioAtual.minusMonths(1);
		
		if(proximaDataExercicio.getMonth() == Month.JANUARY) {
			return proximaDataExercicio.minusMonths(1);
		}
		
		return proximaDataExercicio;
	}
	
	/**
	 * Limpa o texto recebido removendo todo o texto após a primeira abertura de parênteses e também o parênteses.
	 * @param texto - o texto para limpar
	 * @return o texto limpo
	 */
	private String limparTextoDiaReuniao(String texto) {
		int indiceAberturaParenteses = texto.indexOf("(");
		
		if(indiceAberturaParenteses == -1) {
			return texto;
		}
		
		return texto.substring(0, indiceAberturaParenteses).trim();
	}
	
}
