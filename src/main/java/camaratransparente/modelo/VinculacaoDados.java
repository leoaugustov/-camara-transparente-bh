package camaratransparente.modelo;

import java.text.Normalizer;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.JaroWinklerDistance;

import camaratransparente.modelo.entidade.ModeloCusteioParlamentar;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.scrap.CusteioParlamentarMensal;
import camaratransparente.scrap.PresencaMensalIndividual;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VinculacaoDados {
	
	private static final double PERCENTUAL_SIMILARIDADE_MINIMO = 0.9;
	private final JaroWinklerDistance algoritmoCalculoSimilaridade;
	
	
	
	/**
	 * Vincula os dados de custeio parlamentar e presenção mensal consolidada aos vereadores informados. 
	 * @param vereadores - os vereadores aos quais os dados serão vinculados
	 * @param dadosCusteio - os dados de custeio parlamentar que serão vinculados aos vereadores
	 * @param dadosPresenca - os dados de presença mensal consolidada que serão vinculados aos vereadores
	 * @return a lista de vereadores informada contendo as informações vinculadas
	 */
	public List<ModeloVereador> vincular(List<ModeloVereador> vereadores, List<CusteioParlamentarMensal> dadosCusteio, 
			List<PresencaMensalIndividual> dadosPresenca) {
		
		for(CusteioParlamentarMensal dadoCusteio : dadosCusteio) {
			Optional<ModeloVereador> vereador = buscarVereador(vereadores, dadoCusteio.getNomeVereador());
			
			if(vereador.isPresent()) {
				vereador.get().adicionarCusteio(new ModeloCusteioParlamentar(dadoCusteio.getDataReferencia(), 
						dadoCusteio.getValor(), vereador.get()));
			}
		}
		
		for(PresencaMensalIndividual dadoPresencaMensal : dadosPresenca) {
			Optional<ModeloVereador> vereador = buscarVereador(vereadores, dadoPresencaMensal.getNomeVereador());
			
			if(vereador.isPresent()) {
				YearMonth dataExercicio = dadoPresencaMensal.getDataExercicio();
				
				for(Entry<Integer, String> presenca : dadoPresencaMensal.getPresencaMensal().entrySet()) {
					int diaReuniao = presenca.getKey();
					String status = presenca.getValue().trim().replace(" ", "_");
					
					vereador.get().adicionarPresencaReuniao(new ModeloPresencaReuniao(dataExercicio, diaReuniao, status, vereador.get()));
				}
			}
		}
		
		return vereadores;
	}
	
	/**
	 * Tenta buscar o vereador a partir da referência informada. As strings comparadas são normalizadas antes da comparação. 
	 * Caso O vereador não seja encontrado na comparação normal, uma busca por similaridade é realizada.
	 * @param vereadores - a lista de vereadores
	 * @param referencia - a referência ao vereador
	 * @return o vereador encontrado
	 */
	private Optional<ModeloVereador> buscarVereador(List<ModeloVereador> vereadores, String referencia) {
		Map<String, ModeloVereador> vereadoresIndexadosPeloNome = vereadores.stream()
				.collect(Collectors.toMap(vereador -> normalizar(vereador.getNome()), Function.identity()));
		
		ModeloVereador vereadorEncontrado = vereadoresIndexadosPeloNome.get(normalizar(referencia));
		
		if(vereadorEncontrado == null) {
			for(ModeloVereador vereador : vereadores) {
				double percentualSimilaridade = algoritmoCalculoSimilaridade.apply(normalizar(vereador.getNome()), normalizar(referencia));
				
				if(percentualSimilaridade > PERCENTUAL_SIMILARIDADE_MINIMO) {
					return Optional.of(vereador);
				}
			}
		}
		
		return Optional.ofNullable(vereadorEncontrado);
	}
	
	/**
	 * Normaliza o texto informado removendo acentos, caracteres especiais (ç), pontos finais e espaços nas bordas. 
	 * @return o texto normalizado
	 */
	private String normalizar(String texto) {
		return Normalizer.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replace(".", "")
				.toLowerCase()
				.trim();
	}
	
}
