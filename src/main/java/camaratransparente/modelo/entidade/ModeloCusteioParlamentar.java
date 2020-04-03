package camaratransparente.modelo.entidade;

import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "custeio_parlamentar")
@AllArgsConstructor
public class ModeloCusteioParlamentar extends EntidadeBase {

	@Column(nullable = false, columnDefinition = "date")
	private YearMonth dataReferencia;
	
	@Column(nullable = false)
	private double valor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vereador", nullable = false)
	@ToString.Exclude
	private ModeloVereador vereador;
	
}
