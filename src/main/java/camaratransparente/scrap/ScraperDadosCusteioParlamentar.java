package camaratransparente.scrap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScraperDadosCusteioParlamentar {

	private static final String URL_BUSCA_DATA_MAIS_NOVA = "https://www.cmbh.mg.gov.br/transparencia/vereadores/custeio-parlamentar";
	private static final String URL_PESQUISA = "https://www.cmbh.mg.gov.br/sites/all/modules/execucao_orcamentaria_custeio/pesquisar.php";
	private static final YearMonth MENOR_DATA_BUSCADA = YearMonth.of(2017, Month.FEBRUARY);
	
	private final RestTemplate httpClient;
	
	
	
	/**
	 * Busca o histórico de custeio parlamentar de todos os vereadores em todas as datas disponíveis.
	 * @return - uma lista contendo os dados mensais individuais encontrados
	 * @throws IOException caso aconteça algum erro durante o processo
	 */
	public List<CusteioParlamentarMensal> buscarHistoricoCusteio() throws IOException {
		return buscarHistoricoCusteio(MENOR_DATA_BUSCADA);
	}
	
	/**
	 * Busca o histórico de custeio parlamentar de todos os vereadores para a data informada e para todas posteriores.
	 * @param menorDataBuscada - a data mais antiga buscada
	 * @return - uma lista contendo os dados mensais individuais encontrados
	 * @throws IOException caso aconteça algum erro durante o processo
	 */
	public List<CusteioParlamentarMensal> buscarHistoricoCusteio(YearMonth menorDataBuscada) throws IOException {
		if(menorDataBuscada.isBefore(MENOR_DATA_BUSCADA)) {
			menorDataBuscada = MENOR_DATA_BUSCADA;
		}
		
		final YearMonth dataMaisNova = buscarDataMaisNova();
		
		if(menorDataBuscada.isAfter(dataMaisNova)) {
			return Collections.emptyList();
		}
		
		List<CusteioParlamentarMensal> custeios = new ArrayList<>();
		
		YearMonth dataBuscada = dataMaisNova;
		while(dataBuscada.isAfter(menorDataBuscada) || dataBuscada.equals(menorDataBuscada)) {
			Element tabelaResultados = pesquisar(dataBuscada);
			
			if(tabelaResultados != null) {
				//se existir custeio na data
				
				custeios.addAll(buscarDados(dataBuscada, tabelaResultados));
			}
			
			dataBuscada = dataBuscada.minusMonths(1);
		}
		
		return custeios;
	}
	
	private List<CusteioParlamentarMensal> buscarDados(YearMonth dataReferencia, Element tabelaCusteioParlamentar) {
		Elements linhas = tabelaCusteioParlamentar.select("tbody tr");
		
		List<CusteioParlamentarMensal> custeios = new ArrayList<>();
		for(Element linha : linhas) {
			String nomeVereador = linha.children().first().text();
			String valor = linha.children().last().text();
			
			custeios.add(new CusteioParlamentarMensal(dataReferencia, nomeVereador, valor));
		}
		
		return custeios;
	}
	
	private Element pesquisar(YearMonth data) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.setHost(new InetSocketAddress("www.cmbh.mg.gov.br", 0));
		headers.setContentLength(54);
		
		String body = String.format("paginaRequerida=1&codVereador=&mobile=0&data=%d%%2F%d", data.getMonthValue(), data.getYear());
		
		HttpEntity<String> requisicao = new HttpEntity<String>(body, headers);
		String resposta = httpClient.postForObject(URL_PESQUISA, requisicao, String.class);
		
		return Jsoup.parse(resposta).selectFirst("table");
	}
	
	private YearMonth buscarDataMaisNova() throws IOException {
		ResponseEntity<String> resposta = httpClient.exchange(URL_BUSCA_DATA_MAIS_NOVA, HttpMethod.GET, null, String.class);
		Element selectData = Jsoup.parse(resposta.getBody()).getElementById("data");
		
		return selectData.select("option").stream()
				.map(option -> option.attr("value"))
				.map(data -> YearMonth.parse(data, DateTimeFormatter.ofPattern("MM/yyyy")))
				.max(YearMonth::compareTo).get();
	}
	
}
