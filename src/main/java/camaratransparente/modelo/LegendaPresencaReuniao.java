package camaratransparente.modelo;

public enum LegendaPresencaReuniao {

	P, F, AJ, LM, SR, REN, SPL_AC, SPL_MS;
	
	
	
	public static boolean isLegendaValida(String texto) {
		try {
			valueOf(texto);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}
