package camaratransparente.modelo.entidade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import camaratransparente.modelo.LegendaPresencaReuniao;

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
