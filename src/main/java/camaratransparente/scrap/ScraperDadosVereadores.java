package camaratransparente.scrap;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import camaratransparente.modelo.entidade.ModeloVereador;

public class ScraperDadosVereadores {

	private static final String URL_VEREADORES = "https://www.cmbh.mg.gov.br/vereadores";
	
	public List<ModeloVereador> buscarVereadores() throws IOException, InterruptedException {
		Document paginaVereadores = Jsoup.connect(URL_VEREADORES).get();
		Elements links = paginaVereadores.select(".view-vereadores .vereador .views-field-title a");
		
		List<ModeloVereador> vereadores = new ArrayList<>();
		for(Element a : links) {
			String linkPaginaVereador = a.attr("abs:href");
			Document paginaVereador = Jsoup.connect(linkPaginaVereador).get();

			ModeloVereador vereador = new ModeloVereador();
			vereador.setPerfilCmbh(linkPaginaVereador);
			vereador.setNome(buscarNome(paginaVereador));
			vereador.setNomeCivil(buscarNomeCivil(paginaVereador));
			vereador.setFoto(buscarFoto(paginaVereador));
			vereador.setPartido(buscarPartido(paginaVereador));
			
			vereadores.add(vereador);
			
			Thread.sleep(1500); // aguarda para evitar a sobrecarga do site
		}
		
		return vereadores;
	}
	
	
	
	private String buscarNome(Document paginaVereador) {
		return paginaVereador.selectFirst(".field-name-title .field-item h1").text();
	}
	
	private String buscarNomeCivil(Document paginaVereador) {
		return paginaVereador.selectFirst(".field-name-field-nome-civil .field-item").text();
	}
	
	private byte[] buscarFoto(Document paginaVereador) throws IOException {
		String urlFoto = paginaVereador.selectFirst(".views-field-field-foto img").attr("src");
		
		return IOUtils.toByteArray(new URL(urlFoto).openStream());
	}
	
	private String buscarPartido(Document paginaVereador) {
		return paginaVereador.selectFirst(".field-name-partido-composto .field-item").text();
	}
	
}
