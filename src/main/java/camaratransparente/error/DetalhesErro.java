package camaratransparente.error;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DetalhesErro {

	@Setter(AccessLevel.NONE)
	private long timestamp;
	
	private final int status;
	private final String titulo;
	private final String detalhes;
	
	
	
	public DetalhesErro(int status, String titulo, String detalhes) {
		this.status = status;
		this.titulo = titulo;
		this.detalhes = detalhes;
		
		timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
	}
	
	public DetalhesErro(HttpStatus status) {
		this(status.value(), status.getReasonPhrase(), "");
	}
	
}
