package camaratransparente.modelo;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collections;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import camaratransparente.modelo.VinculacaoDados;
import camaratransparente.modelo.entidade.ModeloCusteioParlamentar;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;
import camaratransparente.scrap.CusteioParlamentarMensal;
import camaratransparente.scrap.PresencaMensalIndividual;

@RunWith(MockitoJUnitRunner.class)
public class VinculacaoDadosTest {

	@Mock
	private JaroWinklerDistance algoritmoCalculoSimilaridade;
	
	@Mock
	private ModeloVereador vereador;
	
	private VinculacaoDados servicoVinculacaoDados;
	
	
	
	@Before
	public void inicializarObjetoTestado() {
		servicoVinculacaoDados = new VinculacaoDados(algoritmoCalculoSimilaridade);
	}
	
	
	
	@Test
	public void testarVincular_quando_nomeVereadorComAcentos_referenciaVereadorSemAcentos_apenasDadosCusteioInformados() {
		String nomeVereador = "acentos àáâã";
		String referenciaVereador = "acentos aaaa";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComCaracterEspecial_referenciaVereadorSemCaracterEspecial_apenasDadosCusteioInformados() {
		String nomeVereador = "c cedilha ç";
		String referenciaVereador = "c cedilha c";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComPontuacao_referenciaVereadorSemPontuacao_apenasDadosCusteioInformados() {
		String nomeVereador = "com pontuacao.";
		String referenciaVereador = "com pontuacao";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComMaiusculo_referenciaVereadorSemMaiusculo_apenasDadosCusteioInformados() {
		String nomeVereador = "CoM MaiusCulO";
		String referenciaVereador = nomeVereador.toLowerCase();
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorMenorQue90EmRelacaoReferenciaVereador_apenasDadosCusteioInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.5);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador, never()).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorIgual90EmRelacaoReferenciaVereador_apenasDadosCusteioInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.1);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador, never()).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorMaior90EmRelacaoReferenciaVereador_apenasDadosCusteioInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		CusteioParlamentarMensal custeio = mock(CusteioParlamentarMensal.class);
		when(custeio.getNomeVereador()).thenReturn(referenciaVereador);
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.09);
		
		servicoVinculacaoDados.vincular(asList(vereador), asList(custeio), Collections.emptyList());
		verify(vereador).adicionarCusteio(any(ModeloCusteioParlamentar.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComAcentos_referenciaVereadorSemAcentos_apenasDadosPresencaInformados() {
		String nomeVereador = "acentos àáâã";
		String referenciaVereador = "acentos aaaa";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getDataExercicio()).thenReturn(YearMonth.of(2020, Month.FEBRUARY));
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		when(presenca.getPresencaMensal()).thenReturn(Collections.singletonMap(2, "P"));
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComCaracterEspecial_referenciaVereadorSemCaracterEspecial_apenasDadosPresencaInformados() {
		String nomeVereador = "c cedilha ç";
		String referenciaVereador = "c cedilha c";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getDataExercicio()).thenReturn(YearMonth.of(2020, Month.FEBRUARY));
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		when(presenca.getPresencaMensal()).thenReturn(Collections.singletonMap(2, "P"));
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComPontuacao_referenciaVereadorSemPontuacao_apenasDadosPresencaInformados() {
		String nomeVereador = "com pontuacao.";
		String referenciaVereador = "com pontuacao";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getDataExercicio()).thenReturn(YearMonth.of(2020, Month.FEBRUARY));
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		when(presenca.getPresencaMensal()).thenReturn(Collections.singletonMap(2, "P"));
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_nomeVereadorComMaiusculo_referenciaVereadorSemMaiusculo_apenasDadosPresencaInformados() {
		String nomeVereador = "CoM MaiusCulO";
		String referenciaVereador = nomeVereador.toLowerCase();
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getDataExercicio()).thenReturn(YearMonth.of(2020, Month.FEBRUARY));
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		when(presenca.getPresencaMensal()).thenReturn(Collections.singletonMap(2, "P"));
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorMenorQue90EmRelacaoReferenciaVereador_apenasDadosPresencaInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.5);
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador, never()).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorIgual90EmRelacaoReferenciaVereador_apenasDadosPresencaInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.1);
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador, never()).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
	@Test
	public void testarVincular_quando_similaridadeNomeVereadorMaior90EmRelacaoReferenciaVereador_apenasDadosPresencaInformados() {
		String nomeVereador = "Nome";
		String referenciaVereador = "Referência";
		
		when(vereador.getNome()).thenReturn(nomeVereador);
		
		PresencaMensalIndividual presenca = mock(PresencaMensalIndividual.class);
		when(presenca.getDataExercicio()).thenReturn(YearMonth.of(2020, Month.FEBRUARY));
		when(presenca.getNomeVereador()).thenReturn(referenciaVereador);
		when(presenca.getPresencaMensal()).thenReturn(Collections.singletonMap(2, "P"));
		
		when(algoritmoCalculoSimilaridade.apply(any(String.class), any(String.class))).thenReturn(0.09);
		
		servicoVinculacaoDados.vincular(asList(vereador), Collections.emptyList(), asList(presenca));
		verify(vereador).adicionarPresencaReuniao(any(ModeloPresencaReuniao.class));
	}
	
}
