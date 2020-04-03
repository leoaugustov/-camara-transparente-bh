package camaratransparente.modelo.entidade;

import java.time.LocalDate;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import camaratransparente.LegendaPresencaReuniao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name= "presenca_reuniao")
public class ModeloPresencaReuniao extends EntidadeBase {

	@Column(nullable = false)
	private LocalDate dataReuniao;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LegendaPresencaReuniao status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_vereador", nullable = false)
	@ToString.Exclude
	private ModeloVereador vereador;
	
	
	
	public ModeloPresencaReuniao(YearMonth dataExercicio, int diaReuniao, String statusPresenca, ModeloVereador vereador) {
		dataReuniao = dataExercicio.atDay(diaReuniao);
		status = LegendaPresencaReuniao.valueOf(statusPresenca);
		this.vereador = vereador;
	}
	
}
