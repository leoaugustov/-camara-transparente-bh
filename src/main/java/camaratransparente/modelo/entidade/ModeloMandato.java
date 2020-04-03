package camaratransparente.modelo.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "mandato")
public class ModeloMandato extends EntidadeBase {

	@Column(nullable = false)
	private int anoInicio;
	
	@Column(nullable = false)
	private int anoFinal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vereador", nullable = false)
	@ToString.Exclude
	private ModeloVereador vereador;
	
	
	
	public ModeloMandato(int anoInicio, int anoFinal) {
		this.anoInicio = anoInicio;
		this.anoFinal = anoFinal;
	}
	
}
