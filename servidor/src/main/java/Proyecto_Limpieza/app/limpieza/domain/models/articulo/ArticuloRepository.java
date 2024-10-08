package Proyecto_Limpieza.app.limpieza.domain.models.articulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query(value = "SELECT * FROM articulos a WHERE a.id = :id AND a.sin_stock = False", nativeQuery = true)
    Optional<Articulo> findByIdAndStock(@Param("id") Long id);


    @Query(value = "SELECT * FROM articulos a WHERE a.nombre = :name AND a.sin_stock = False", nativeQuery = true)
    Optional<Articulo> findByNameAndStock(String name);

    @Query(value = "SELECT * FROM articulos a WHERE a.nombre LIKE %:query% AND a.sin_stock = False", nativeQuery = true)
    List<Articulo> findByParam(@Param("query") String query);
}
