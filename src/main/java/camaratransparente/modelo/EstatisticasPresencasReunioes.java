package camaratransparente.modelo;

import java.util.List;

import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import lombok.Getter;

@Getter
public class EstatisticasPresencasReunioes {

	private double frequencia = 1;
	private int faltas = 0;
	private int ausenciasJustificadas = 0;
	
	
	
	public EstatisticasPresencasReunioes(List<ModeloPresencaReuniao> presencasReunioes) {
		for(ModeloPresencaReuniao presenca : presencasReunioes) {
			if(presenca.isFalta()) {
				faltas++;
			}else if(presenca.isAusenciaJustificada()) {
				ausenciasJustificadas++;
			}
		}
		
		if(presencasReunioes.size() != 0) {
			frequencia = 1 - ((double) faltas + ausenciasJustificadas) / presencasReunioes.size();
		}
	}
	
}
