package camaratransparente.scrap;

import java.time.YearMonth;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CusteioParlamentarMensal {

	private final YearMonth dataReferencia;
	private final String nomeVereador;
	private final double valor;
	
	
	
	public CusteioParlamentarMensal(YearMonth dataReferencia, String nomeVereador, String valor) {
		this.dataReferencia = dataReferencia;
		this.nomeVereador = nomeVereador;
		this.valor = Double.parseDouble(valor.replace("R$", "").replace(".", "").replace(",", "."));
	}
	
}
