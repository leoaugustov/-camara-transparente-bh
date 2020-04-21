package camaratransparente.modelo.entidade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import camaratransparente.modelo.entidade.ModeloCusteioParlamentar;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;

public class ModeloVereadorTest {

	private static final double DELTA = 0.001;
	
	@Test
	public void testarGetMaiorDataReferenciaCusteio_quando_vereadorNaoPossuiInformacoesCusteio() {
		ModeloVereador vereador = new ModeloVereador();
		assertThat(vereador.getMaiorDataReferenciaCusteio()).isEmpty();
	}
	
	@Test
	public void testarGetMaiorDataReferenciaCusteio_quando_vereadorPossuiInformacoesCusteio() {
		YearMonth dataReferencia = YearMonth.of(2020, Month.FEBRUARY);
		
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloCusteioParlamentar custeio = mock(ModeloCusteioParlamentar.class);
		when(custeio.getDataReferencia()).thenReturn(dataReferencia);
		vereador.adicionarCusteio(custeio);
		
		assertThat(vereador.getMaiorDataReferenciaCusteio()).contains(dataReferencia);
	}
	
	@Test
	public void testarGetMaiorDataExercicioPresenca_quando_vereadorNaoPossuiInformacoesPresenca() {
		ModeloVereador vereador = new ModeloVereador();
		assertThat(vereador.getMaiorDataExercicioPresenca()).isEmpty();
	}
	
	@Test
	public void testarGetMaiorDataExercicioPresenca_quando_vereadorPossuiInformacoesPresenca() {
		LocalDate dataReuniao = LocalDate.of(2020, Month.FEBRUARY, 3);
		
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		when(presenca.getDataReuniao()).thenReturn(dataReuniao);
		vereador.adicionarPresencaReuniao(presenca);
		
		assertThat(vereador.getMaiorDataExercicioPresenca()).contains(YearMonth.from(dataReuniao));
	}
	
	@Test
	public void testarGetFrequencia_quando_naoPossuiInformacoesPresenca() {
		ModeloVereador vereador = new ModeloVereador();
		assertEquals(1, vereador.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetFrequencia_quando_possuiInformacoesPresencaComFalta() {
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloPresencaReuniao presente = mock(ModeloPresencaReuniao.class);
		vereador.adicionarPresencaReuniao(presente);
		
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.isFalta()).thenReturn(true);
		vereador.adicionarPresencaReuniao(falta);
		
		assertEquals(0.5, vereador.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetFrequencia_quando_possuiInformacoesPresencaComAusenciaJustificada() {
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloPresencaReuniao presente = mock(ModeloPresencaReuniao.class);
		vereador.adicionarPresencaReuniao(presente);
		
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.isAusenciaJustificada()).thenReturn(true);
		vereador.adicionarPresencaReuniao(falta);
		
		assertEquals(0.5, vereador.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetQuantidadeFaltas_quando_possuiInformacoesPresencaComFalta() {
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloPresencaReuniao presente = mock(ModeloPresencaReuniao.class);
		vereador.adicionarPresencaReuniao(presente);
		
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.isFalta()).thenReturn(true);
		vereador.adicionarPresencaReuniao(falta);
		
		assertEquals(1, vereador.getQuantidadeFaltas());
	}
	
	@Test
	public void testarGetQuantidadeAusenciasJustificadas_quando_possuiInformacoesPresencaComAusenciaJustificada() {
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloPresencaReuniao presente = mock(ModeloPresencaReuniao.class);
		vereador.adicionarPresencaReuniao(presente);
		
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.isAusenciaJustificada()).thenReturn(true);
		vereador.adicionarPresencaReuniao(falta);
		
		assertEquals(1, vereador.getQuantidadeAusenciasJustificadas());
	}
	
	@Test
	public void testarGetMaiorCusteioMensal_quando_naoPossuiInformacoesCusteio() {
		ModeloVereador vereador = new ModeloVereador();
		assertEquals(0, vereador.getMaiorCusteioMensal(), DELTA);
	}
	
	@Test
	public void testarGetMaiorCusteioMensal_quando_possuiInformacoesCusteio() {
		double maiorCusteioMensal = 400;
		ModeloVereador vereador = new ModeloVereador();
		
		ModeloCusteioParlamentar custeio1 = mock(ModeloCusteioParlamentar.class);
		when(custeio1.getValor()).thenReturn(200.0);
		vereador.adicionarCusteio(custeio1);
		
		ModeloCusteioParlamentar custeio2 = mock(ModeloCusteioParlamentar.class);
		when(custeio2.getValor()).thenReturn(maiorCusteioMensal);
		vereador.adicionarCusteio(custeio2);
		
		assertEquals(maiorCusteioMensal, vereador.getMaiorCusteioMensal(), DELTA);
	}
	
}
