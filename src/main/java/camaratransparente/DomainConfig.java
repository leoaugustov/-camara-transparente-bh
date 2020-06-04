package camaratransparente;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import camaratransparente.modelo.VinculacaoDados;
import camaratransparente.scrap.ScraperDadosCusteioParlamentar;
import camaratransparente.scrap.ScraperDadosPresencaMensal;
import camaratransparente.scrap.ScraperDadosVereadores;

@Configuration
public class DomainConfig {

	@Bean
	public JaroWinklerDistance algoritmoCalculoSimilaridade() {
		return new JaroWinklerDistance();
	}
	
	@Bean
	public VinculacaoDados servicoVinculacaoDados(JaroWinklerDistance algoritmoCalculoSimilaridade) {
		return new VinculacaoDados(algoritmoCalculoSimilaridade);
	}
	
	@Bean
	public ScraperDadosVereadores scraperDadosVereadores() {
		return new ScraperDadosVereadores();
	}
	
	@Bean
	public ScraperDadosCusteioParlamentar scraperDadosCusteioParlamentar(RestTemplate restTemplate) {
		return new ScraperDadosCusteioParlamentar(restTemplate);
	}
	
	@Bean
	public ScraperDadosPresencaMensal scraperDadosPresencaMensal(RestTemplate restTemplate) {
		return new ScraperDadosPresencaMensal(restTemplate);
	}
	
}