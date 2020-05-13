package camaratransparente.modelo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import camaratransparente.modelo.entidade.ModeloPresencaReuniao;

public class EstatisticasPresencasReunioesTest {

	private static final double DELTA = 0.001;
	
	
	
	@Test
	public void testarGetQuantidadeExercicios_quando_comDuasPresencasEmExerciciosDiferentes() {
		ModeloPresencaReuniao presenca1 = criarPresenca();
		when(presenca1.getDataExercicio()).thenReturn(Optional.of(YearMonth.of(2019, Month.SEPTEMBER)));
		
		ModeloPresencaReuniao presenca2 = criarPresenca();
		when(presenca2.getDataExercicio()).thenReturn(Optional.of(YearMonth.of(2019, Month.OCTOBER)));
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca1, presenca2));
		
		assertEquals(2, estatisticas.getQuantidadeExercicios());
	}
	
	@Test
	public void testarGetQuantidadeExercicios_quando_comDuasFaltasEmExerciciosDiferentes() {
		ModeloPresencaReuniao falta1 = criarFalta();
		when(falta1.getDataExercicio()).thenReturn(Optional.of(YearMonth.of(2019, Month.SEPTEMBER)));
		
		ModeloPresencaReuniao falta2 = criarFalta();
		when(falta2.getDataExercicio()).thenReturn(Optional.of(YearMonth.of(2019, Month.OCTOBER)));
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(falta1, falta2));
		
		assertEquals(2, estatisticas.getQuantidadeExercicios());
	}
	
	@Test
	public void testarGetFrequencia_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		assertEquals(0, estatisticas.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetFrequencia_quando_comUmaPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca));
		
		assertEquals(1, estatisticas.getFrequencia(), DELTA);
	}

	@Test
	public void testarGetFrequencia_quando_comUmaPresenca_comUmaFalta() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, falta));
		
		assertEquals(0.5, estatisticas.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetFrequencia_quando_comUmaPresenca_comUmaAusenciaJustificada() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, ausenciaJustificada));
		
		assertEquals(0.5, estatisticas.getFrequencia(), DELTA);
	}

	@Test
	public void testarGetFrequencia_quando_comUmaPresenca_comUmaLicencaMedica() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao licencaMedica = criarLicencaMedica();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, licencaMedica));
		
		assertEquals(0.5, estatisticas.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetFrequencia_quando_comUmaPresenca_comUmaLicencasNaoRemuneradas() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, licencanaoRemunerada));
		
		assertEquals(0.5, estatisticas.getFrequencia(), DELTA);
	}
	
	@Test
	public void testarGetPresencas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetPresencas_quando_comPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca));
		
		assertEquals(1, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetPresencas_quando_comFalta() {
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(falta));
		
		assertEquals(0, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetPresencas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(ausenciaJustificada));
		
		assertEquals(0, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetPresencas_quando_comLicencaMedica() {
		ModeloPresencaReuniao licencaMedica = criarLicencaMedica();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencaMedica));
		
		assertEquals(0, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetPresencas_quando_comLicencasNaoRemuneradas() {
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencanaoRemunerada));
		
		assertEquals(0, estatisticas.getPresencas());
	}
	
	@Test
	public void testarGetFaltas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetFaltas_quando_comPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca));
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetFaltas_quando_comFalta() {
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(falta));
		
		assertEquals(1, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetFaltas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(ausenciaJustificada));
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetFaltas_quando_comLicencaMedica() {
		ModeloPresencaReuniao ausenciaJustificada = criarLicencaMedica();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(ausenciaJustificada));
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetFaltas_quando_comLicencasNaoRemuneradas() {
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencanaoRemunerada));
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_comPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca));
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_comFalta() {
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(falta));
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(ausenciaJustificada));
		
		assertEquals(1, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_comLicencaMedica() {
		ModeloPresencaReuniao licencaMedica = criarLicencaMedica();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencaMedica));
		
		assertEquals(1, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetAusenciasJustificadas_quando_comLicencasNaoRemuneradas() {
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencanaoRemunerada));
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_comPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca));
		
		assertEquals(0, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_comFalta() {
		ModeloPresencaReuniao falta = criarFalta();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(falta));
		
		assertEquals(0, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(ausenciaJustificada));
		
		assertEquals(0, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_comLicencaMedica() {
		ModeloPresencaReuniao licencaMedica = criarLicencaMedica();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencaMedica));
		
		assertEquals(0, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetLicencasNaoRemuneradas_quando_comLicencasNaoRemuneradas() {
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(licencanaoRemunerada));
		
		assertEquals(1, estatisticas.getLicencasNaoRemuneradas());
	}
	
	@Test
	public void testarGetTotalAusencias_quando_comFalta_comLicencasNaoRemuneradas_comAusenciaJustificada_comPresenca() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao falta = criarFalta();
		ModeloPresencaReuniao licencanaoRemunerada = criarLicencanaoRemunerada();
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		ModeloPresencaReuniao licencaMedica = criarLicencaMedica();
		
		List<ModeloPresencaReuniao> lsPresencasReunioes = Arrays.asList(presenca, falta, 
				licencaMedica, ausenciaJustificada, licencanaoRemunerada);
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(lsPresencasReunioes);
		
		assertEquals(4, estatisticas.getTotalAusencias());
	}
	
	
	
	private ModeloPresencaReuniao criarPresenca() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		when(presenca.getStatus()).thenReturn(LegendaPresencaReuniao.P);
		when(presenca.getDataExercicio()).thenReturn(Optional.of(YearMonth.now()));
		
		return presenca;
	}
	
	private ModeloPresencaReuniao criarFalta() {
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.getStatus()).thenReturn(LegendaPresencaReuniao.F);
		when(falta.getDataExercicio()).thenReturn(Optional.of(YearMonth.now()));
		
		return falta;
	}
	
	private ModeloPresencaReuniao criarLicencaMedica() {
		ModeloPresencaReuniao licencaMedica = mock(ModeloPresencaReuniao.class);
		when(licencaMedica.getStatus()).thenReturn(LegendaPresencaReuniao.LM);
		when(licencaMedica.getDataExercicio()).thenReturn(Optional.of(YearMonth.now()));
		
		return licencaMedica;
	}
	
	private ModeloPresencaReuniao criarAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = mock(ModeloPresencaReuniao.class);
		when(ausenciaJustificada.getStatus()).thenReturn(LegendaPresencaReuniao.AJ);
		when(ausenciaJustificada.getDataExercicio()).thenReturn(Optional.of(YearMonth.now()));
		
		return ausenciaJustificada;
	}
	
	private ModeloPresencaReuniao criarLicencanaoRemunerada() {
		ModeloPresencaReuniao licencaNaoRemunerada = mock(ModeloPresencaReuniao.class);
		when(licencaNaoRemunerada.getStatus()).thenReturn(LegendaPresencaReuniao.SR);
		when(licencaNaoRemunerada.getDataExercicio()).thenReturn(Optional.of(YearMonth.now()));
		
		return licencaNaoRemunerada;
	}
			
}
