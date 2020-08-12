package camaratransparente.repositorio;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import camaratransparente.modelo.entidade.ModeloVereador;

public interface RepositorioVereador extends JpaRepository<ModeloVereador, Long> {

	boolean existsByUuid(UUID uuid);
	
	@EntityGraph(attributePaths = {"custeios"})
	@Query("SELECT v FROM ModeloVereador v")
	List<ModeloVereador> buscarTodosComCusteio();
	
	@EntityGraph(attributePaths = {"presencasReunioes"})
	@Query("SELECT v FROM ModeloVereador v")
	List<ModeloVereador> buscarTodosComPresencasReunioes();
	
	@Query("SELECT v.foto FROM ModeloVereador v WHERE v.uuid = ?1")
	byte[] buscarFotoPorUuid(UUID uuid);
	
}
