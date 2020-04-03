package camaratransparente.modelo.entidade;

import static org.assertj.core.api.Assertions.assertThat;
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
	
}
