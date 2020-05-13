package camaratransparente.modelo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModeloCusteioPorPartidoDto {

	private final String siglaPartido;
	private final long quantidadeVereadores;
	private final double custeio;
	
}