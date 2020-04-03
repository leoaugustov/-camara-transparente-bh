package camaratransparente.scrap;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class PresencaMensalIndividual {

	private final String nomeVereador;
	private final YearMonth dataExercicio;
	private Map<Integer, String> presencaMensal = new HashMap<>();
	
	
	
	public void adicionarPresenca(int dia, String status) {
		presencaMensal.put(dia, status);
	}
	
}
