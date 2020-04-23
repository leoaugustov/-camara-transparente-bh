package camaratransparente.modelo.entidade;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import camaratransparente.modelo.LegendaPresencaReuniao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name= "presenca_reuniao")
@NoArgsConstructor
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
	
	
	
	public Optional<YearMonth> getDataExercicio() {
		return Optional.ofNullable(dataReuniao).map(YearMonth::from);
	}
	
	public boolean isPresenca() {
		if(status == null) {
			return false;
		}
		
		return status == LegendaPresencaReuniao.P;
	}
	
	public boolean isFalta() {
		if(isPresenca()) {
			return false;
		}
		
		return status == LegendaPresencaReuniao.F;
	}
	
	/**
	 * Verifica se o status é de ausência justificada ou licença médica.
	 */
	public boolean isAusenciaJustificada() {
		if(isPresenca()) {
			return false;
		}
		
		return status == LegendaPresencaReuniao.AJ || status == LegendaPresencaReuniao.LM;
	}
	
}
