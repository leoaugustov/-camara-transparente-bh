package camaratransparente.modelo.entidade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import camaratransparente.LegendaPresencaReuniao;
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
	
}
