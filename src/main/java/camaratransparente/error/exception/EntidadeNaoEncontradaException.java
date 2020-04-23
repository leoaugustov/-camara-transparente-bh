package camaratransparente.error.exception;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(Object... parametrosBusca) {
		super(gerarMensagem(parametrosBusca));
	}
	
	private static String gerarMensagem(Object... parametrosBusca) {
		StringBuilder mensagem = new StringBuilder("Entidade não encontrada para os paramêtros [");
		mensagem.append(String.join(",", Arrays.asList(parametrosBusca).stream()
				.map(Object::toString)
				.collect(toList())));
		
		return mensagem.append("].").toString();
	}
	
}
