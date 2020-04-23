package camaratransparente.modelo;

import java.util.List;

import camaratransparente.modelo.entidade.ModeloPresencaReuniao;
import lombok.Getter;

@Getter
public class EstatisticasPresencasReunioes {

	private double frequencia;
	private int faltas;
	private int ausenciasJustificadas;
	
	
	
	public EstatisticasPresencasReunioes(List<ModeloPresencaReuniao> presencasReunioes) {
		int presencas = 0;
		
		for(ModeloPresencaReuniao presencaReuniao : presencasReunioes) {
			if(presencaReuniao.isPresenca()) {
				presencas++;
			}else if(presencaReuniao.isFalta()) {
				faltas++;
			}else if(presencaReuniao.isAusenciaJustificada()) {
				ausenciasJustificadas++;
			}
		}
		
		if(presencasReunioes.size() != 0) {
			frequencia = ((double) presencas / presencasReunioes.size());
		}
	}
	
}
