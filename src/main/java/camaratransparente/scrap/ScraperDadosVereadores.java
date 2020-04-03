package camaratransparente.scrap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import camaratransparente.modelo.entidade.ModeloMandato;
import camaratransparente.modelo.entidade.ModeloVereador;

public class ScraperDadosVereadores {

	private static final String URL_VEREADORES = "https://www.cmbh.mg.gov.br/vereadores";
	
	public List<ModeloVereador> buscarVereadores() throws IOException {
		Document paginaVereadores = Jsoup.connect(URL_VEREADORES).get();
		Elements links = paginaVereadores.select(".view-vereadores .vereador .views-field-title a");
		
		List<ModeloVereador> vereadores = new ArrayList<>();
		for(Element a : links) {
			String linkPaginaVereador = a.attr("abs:href");
			Document paginaVereador = Jsoup.connect(linkPaginaVereador).get();

			ModeloVereador vereador = new ModeloVereador();
			vereador.setNome(buscarNome(paginaVereador));
			vereador.setNomeCivil(buscarNomeCivil(paginaVereador));
			vereador.setFoto(buscarFoto(paginaVereador));
			vereador.setPartido(buscarPartido(paginaVereador));
			vereador.setEmail(buscarEmail(paginaVereador));
			vereador.adicionarMandatos(buscarMandatos(paginaVereador));
			
			vereadores.add(vereador);
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
		
		byte[] imagem;
		try(InputStream in = new URL(urlFoto).openStream()) {
			imagem = new byte[in.available()];
			in.read(imagem);
		}
	
		return imagem;
	}
	
	private String buscarPartido(Document paginaVereador) {
		return paginaVereador.selectFirst(".field-name-partido-composto .field-item").text();
	}
	
	private String buscarEmail(Document paginaVereador) {
		return Optional.ofNullable(paginaVereador.selectFirst(".views-field-field-email a"))
				.map(Element::text)
				.orElse(null);
	}
	
	private List<ModeloMandato> buscarMandatos(Document paginaVereador) {
		Elements spansMandatos = paginaVereador.select(".field-name-field-periodo .date-display-range");
		
		List<ModeloMandato> mandatos = new ArrayList<>();
		for(Element spanMandato : spansMandatos) {
			int inicioMandato = Integer.parseInt(spanMandato.selectFirst(".date-display-start").text());
			int finalMandato = Integer.parseInt(spanMandato.selectFirst(".date-display-end").text());
			
			mandatos.add(new ModeloMandato(inicioMandato, finalMandato));
		}
		
		return mandatos;
	}
	
}
