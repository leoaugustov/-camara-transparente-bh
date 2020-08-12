package camaratransparente.modelo.entidade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ModeloVereadorTest {

	private static final double DELTA = 0.001;
	
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
