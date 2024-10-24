package Proyecto_Limpieza.app.limpieza.domain.models.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

//    no hace falta crear la query manualmente, JPA lo hace automatico
    @Query(value = "SELECT * FROM categorias c WHERE c.name = :name", nativeQuery = true)
    Optional<Categoria> findByName(@Param("name")String name);
}