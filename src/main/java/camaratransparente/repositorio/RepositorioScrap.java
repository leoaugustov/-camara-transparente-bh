package camaratransparente.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import camaratransparente.modelo.entidade.ModeloScrap;

public interface RepositorioScrap extends JpaRepository<ModeloScrap, Long> {

	Optional<ModeloScrap> findTopByOrderByIdDesc();
	
}
