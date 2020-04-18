package camaratransparente.modelo.entidade;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@ToString
@Setter
@Getter
public abstract class EntidadeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (! (obj instanceof EntidadeBase)) {
			return false;
		}
		
		EntidadeBase other = (EntidadeBase) obj;
		return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
	
}
