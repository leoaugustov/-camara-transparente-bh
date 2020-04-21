package camaratransparente.modelo.dto;

import camaratransparente.modelo.entidade.ModeloVereador;
import lombok.Getter;

@Getter
public class ModeloVereadorDto {

	private final Long id;
	private final String nome;
	private final String linkFoto;
	private final double frequencia;
	private final long quantidadeFaltas;
	private final long quantidadeAusenciasJustificadas;
	private final double maiorCusteioMensal;
	private final double custeioTotal;
	
	
	
	public ModeloVereadorDto(ModeloVereador vereador) {
		id = vereador.getId();
		nome = vereador.getNome();
		linkFoto = vereador.getLinkFoto();
		frequencia = vereador.getFrequencia();
		quantidadeFaltas = vereador.getQuantidadeFaltas();
		quantidadeAusenciasJustificadas = vereador.getQuantidadeAusenciasJustificadas();
		maiorCusteioMensal = vereador.getMaiorCusteioMensal();
		custeioTotal = vereador.getCusteioTotal();
	}
	
}
