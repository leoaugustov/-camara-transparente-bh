package camaratransparente.scrap;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Month;
import java.time.YearMonth;

import org.junit.Test;

import camaratransparente.scrap.CusteioParlamentarMensal;

public class CusteioParlamentarMensalTest {

	private static final YearMonth DATA_REFERENCIA = YearMonth.of(2019, Month.DECEMBER);
	private static final String NOME_VEREADOR = "José da Silva";
	
	@Test
	public void testarInicializacao_quando_valorPossuiDoisSeparadoresMilhar() {
		CusteioParlamentarMensal custeioParlamentar = new CusteioParlamentarMensal(DATA_REFERENCIA, NOME_VEREADOR, "R$ 1.342.231,23");
		
		assertThat(custeioParlamentar.getValor()).isEqualTo(1342231.23);
	}
	
	@Test
	public void testarInicializacao_quando_valorNaoPossuiSeparadorMilhar() {
		CusteioParlamentarMensal custeioParlamentar = new CusteioParlamentarMensal(DATA_REFERENCIA, NOME_VEREADOR, "R$ 231,23");
		
		assertThat(custeioParlamentar.getValor()).isEqualTo(231.23);
	}
	
}
