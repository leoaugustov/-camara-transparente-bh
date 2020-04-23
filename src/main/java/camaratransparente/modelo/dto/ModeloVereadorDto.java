package camaratransparente.modelo.dto;

import camaratransparente.modelo.EstatisticasPresencasReunioes;
import camaratransparente.modelo.entidade.ModeloVereador;
import lombok.Getter;

@Getter
public class ModeloVereadorDto {

	private final Long id;
	private final String nome;
	private final String linkFoto;
	private final double maiorCusteioMensal;
	private final double custeioTotal;
	private final EstatisticasPresencasReunioes estatisticasPresencasReunioes;
	
	
	
	public ModeloVereadorDto(ModeloVereador vereador, EstatisticasPresencasReunioes estatisticasPresencasReunioes) {
		id = vereador.getId();
		nome = vereador.getNome();
		linkFoto = vereador.getLinkFoto();
		maiorCusteioMensal = vereador.getMaiorCusteioMensal();
		custeioTotal = vereador.getCusteioTotal();
		this.estatisticasPresencasReunioes = estatisticasPresencasReunioes;
	}
	
}
