package camaratransparente.error.exception;

import lombok.Getter;

public class QuantidadeRequisicoesEsgotadaException extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private final long segundosAteProximaTentativa;
	
	
	
	public QuantidadeRequisicoesEsgotadaException(long segundosAteProximaTentativa) {
		super("A quantidade máxima de requisições em um determinado período de tempo foi esgotada.");
		this.segundosAteProximaTentativa = segundosAteProximaTentativa;
	}
	
}
