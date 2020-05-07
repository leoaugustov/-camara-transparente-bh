package camaratransparente.modelo.dto;

import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.modelo.entidade.ModeloVereador;
import lombok.Getter;

@Getter
public class ModeloVereadorDto {

	private final Long id;
	private final String nome;
	private final double maiorCusteioMensal;
	private final double custeioTotal;
	private final EstatisticasPresencasReunioes estatisticasPresencas;
	
	
	
	public ModeloVereadorDto(ModeloVereador vereador, EstatisticasPresencasReunioes estatisticasPresencas) {
		id = vereador.getId();
		nome = vereador.getNome();
		maiorCusteioMensal = vereador.getMaiorCusteioMensal();
		custeioTotal = vereador.getCusteioTotal();
		this.estatisticasPresencas = estatisticasPresencas;
	}
	
}
