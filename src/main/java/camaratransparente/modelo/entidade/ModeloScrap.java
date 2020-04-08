package camaratransparente.modelo.entidade;

import java.time.LocalDateTime;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "scrap")
@NoArgsConstructor
public class ModeloScrap extends EntidadeBase {

	@Column(nullable = false)
	private LocalDateTime dataExecucao;
	
	@Column(nullable = false, columnDefinition = "date")
	private YearMonth ultimaDataBuscadaCusteio;
	
	@Column(nullable = false, columnDefinition = "date")
	private YearMonth ultimoExercicioBuscadoPresenca;
	
	
	
	public ModeloScrap(YearMonth ultimaDataBuscadaCusteio, YearMonth ultimoExercicioBuscadoPresenca) {
		this.ultimaDataBuscadaCusteio = ultimaDataBuscadaCusteio;
		this.ultimoExercicioBuscadoPresenca = ultimoExercicioBuscadoPresenca;
	}
	
	
	
	@PrePersist
	private void prePersistencia() {
		dataExecucao = LocalDateTime.now();
	}
	
}
