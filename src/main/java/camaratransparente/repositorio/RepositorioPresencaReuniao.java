package camaratransparente.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import camaratransparente.modelo.entidade.ModeloPresencaReuniao;

public interface RepositorioPresencaReuniao extends JpaRepository<ModeloPresencaReuniao, Long> {

	List<ModeloPresencaReuniao> findByVereadorId(Long idVereador);
	
}
