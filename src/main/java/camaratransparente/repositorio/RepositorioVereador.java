package camaratransparente.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import camaratransparente.modelo.entidade.ModeloVereador;

public interface RepositorioVereador extends JpaRepository<ModeloVereador, Long> {

	@EntityGraph(attributePaths = {"custeios", "presencasReunioes"})
	@Query("SELECT v FROM ModeloVereador v")
	List<ModeloVereador> buscarTodosComCusteioComPresenca();
	
}
