package camaratransparente.modelo.entidade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import camaratransparente.modelo.LegendaPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import camaratransparente.modelo.entidade.ModeloVereador;

public class ModeloPresencaReuniaoTest {

	@Test
	public void testarInicializacao_quando_todosParametrosValidos() {
		YearMonth dataExercicio = YearMonth.of(2020, Month.JANUARY);
		int diaReuniao = 17;
		ModeloVereador vereador = mock(ModeloVereador.class);
		
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao(dataExercicio, diaReuniao, "P", vereador);
		
		assertThat(presenca.getDataReuniao()).isEqualTo(dataExercicio.atDay(diaReuniao));
		assertThat(presenca.getStatus()).isEqualTo(LegendaPresencaReuniao.P);
		assertThat(presenca.getVereador()).isEqualTo(vereador);
	}
	
	@Test
	public void testarIsPresenca_quando_naoPossuiStatus() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		assertFalse(presenca.isPresenca());
	}
	
	@Test
	public void testarIsPresenca_quando_possuiStatusPresenca() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		presenca.setStatus(LegendaPresencaReuniao.P);
		
		assertTrue(presenca.isPresenca());
	}
	
	@Test
	public void testarIsPresenca_quando_statusNaoEhPresenca() {
		for(LegendaPresencaReuniao status : LegendaPresencaReuniao.values()) {
			if(status != LegendaPresencaReuniao.P) {
				ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
				presenca.setStatus(status);
				
				assertFalse("Falha quando o status de presença é: " + status, presenca.isPresenca());
			}
		}
	}
	
	@Test
	public void testarIsFalta_quando_naoPossuiStatus() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		assertFalse(presenca.isFalta());
	}
	
	@Test
	public void testarIsFalta_quando_possuiStatusFalta() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		presenca.setStatus(LegendaPresencaReuniao.F);
		
		assertTrue(presenca.isFalta());
	}
	
	@Test
	public void testarIsFalta_quando_statusNaoEhFalta() {
		for(LegendaPresencaReuniao status : LegendaPresencaReuniao.values()) {
			if(status != LegendaPresencaReuniao.F) {
				ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
				presenca.setStatus(status);
				
				assertFalse("Falha quando o status de presença é: " + status, presenca.isFalta());
			}
		}
	}
	
	@Test
	public void testarIsAusenciaJustificada_quando_naoPossuiStatus() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		assertFalse(presenca.isAusenciaJustificada());
	}
	
	@Test
	public void testarIsAusenciaJustificada_quando_possuiStatusAusenciaMedica() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		presenca.setStatus(LegendaPresencaReuniao.AJ);
		
		assertTrue(presenca.isAusenciaJustificada());
	}
	
	@Test
	public void testarIsAusenciaJustificada_quando_possuiStatusLicencaMedica() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		presenca.setStatus(LegendaPresencaReuniao.LM);
		
		assertTrue(presenca.isAusenciaJustificada());
	}
	
	@Test
	public void testarIsAusenciaJustificada_quando_statusNaoEhAusenciaMedica_statusNaoEhLicencaMedica() {
		for(LegendaPresencaReuniao status : LegendaPresencaReuniao.values()) {
			if(status != LegendaPresencaReuniao.AJ && status != LegendaPresencaReuniao.LM) {
				ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
				presenca.setStatus(status);
				
				assertFalse("Falha quando o status de presença é: " + status, presenca.isAusenciaJustificada());
			}
		}
	}
	
	@Test
	public void testarGetDataExercicio_quando_dataReunicaoNula() {
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		assertThat(presenca.getDataExercicio()).isEmpty();
	}
	
	@Test
	public void testarGetDataExercicio_quando_dataReunicaoNaoNula() {
		LocalDate dataReuniao = LocalDate.of(2018, Month.APRIL, 1);
		
		ModeloPresencaReuniao presenca = new ModeloPresencaReuniao();
		presenca.setDataReuniao(dataReuniao);
		assertThat(presenca.getDataExercicio()).contains(YearMonth.from(dataReuniao));
	}
	
}
