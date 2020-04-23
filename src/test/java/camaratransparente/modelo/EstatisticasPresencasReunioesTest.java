package camaratransparente.modelo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import camaratransparente.modelo.entidade.ModeloPresencaReuniao;

public class EstatisticasPresencasReunioesTest {

	private static final double DELTA = 0.001;
	
	
	
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
	public void testarGetFrequencia_quando_comUmaPresenca_comUmaAusenciaJustificada() {
		ModeloPresencaReuniao presenca = criarPresenca();
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, ausenciaJustificada));
		
		assertEquals(0.5, estatisticas.getFrequencia(), DELTA);
	}

	@Test
	public void testarGetQuantidadeFaltas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetQuantidadeFaltas_quando_comFalta() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, falta));
		
		assertEquals(1, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetQuantidadeFaltas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, ausenciaJustificada));
		
		assertEquals(0, estatisticas.getFaltas());
	}
	
	@Test
	public void testarGetQuantidadeAusenciasJustificadas_quando_semPresencasReunioes() {
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Collections.emptyList());
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetQuantidadeAusenciasJustificadas_quando_comFalta() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		ModeloPresencaReuniao falta = criarFalta();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, falta));
		
		assertEquals(0, estatisticas.getAusenciasJustificadas());
	}
	
	@Test
	public void testarGetQuantidadeAusenciasJustificadas_quando_comAusenciaJustificada() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		ModeloPresencaReuniao ausenciaJustificada = criarAusenciaJustificada();
		
		EstatisticasPresencasReunioes estatisticas = new EstatisticasPresencasReunioes(Arrays.asList(presenca, ausenciaJustificada));
		
		assertEquals(1, estatisticas.getAusenciasJustificadas());
	}
	
	
	
	private ModeloPresencaReuniao criarPresenca() {
		ModeloPresencaReuniao presenca = mock(ModeloPresencaReuniao.class);
		when(presenca.isPresenca()).thenReturn(true);
		
		return presenca;
	}
	
	private ModeloPresencaReuniao criarFalta() {
		ModeloPresencaReuniao falta = mock(ModeloPresencaReuniao.class);
		when(falta.isFalta()).thenReturn(true);
		
		return falta;
	}
	
	private ModeloPresencaReuniao criarAusenciaJustificada() {
		ModeloPresencaReuniao ausenciaJustificada = mock(ModeloPresencaReuniao.class);
		when(ausenciaJustificada.isAusenciaJustificada()).thenReturn(true);
		
		return ausenciaJustificada;
	}
			
			
}
