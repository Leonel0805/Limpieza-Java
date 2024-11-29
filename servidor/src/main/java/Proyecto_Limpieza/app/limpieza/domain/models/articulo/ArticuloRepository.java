package Proyecto_Limpieza.app.limpieza.domain.models.articulo;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    @Query(value = "SELECT * FROM articulos a WHERE a.is_active = true ORDER BY a.stock DESC", nativeQuery = true)
    List<Articulo> findAllIsActiveAndOrderStock();


    @Query(value = """
        SELECT *
        FROM articulos a
        WHERE (:categoriaId IS NULL OR a.categoria_id = :categoriaId)
          AND (:precio IS NULL OR a.precio <= :precio)
          AND (:isActive IS NULL OR a.is_active = :isActive)
          AND (:nombre IS NULL OR a.nombre LIKE %:nombre%)
        """, nativeQuery = true)
    List<Articulo> findAllWithFilters(@Param("categoriaId") Long categoriaId,
                                      @Param("precio") Double precio,
                                      @Param("isActive") Boolean isActive,
                                      @Param("nombre") String nombre);

    @Query(value = "SELECT * FROM articulos a WHERE a.id = :id AND a.is_active = true", nativeQuery = true)
    Optional<Articulo> findByIdIsActive(@Param("id") Long id);


    @Query(value = "SELECT * FROM articulos a WHERE a.nombre = :name AND a.is_active = true", nativeQuery = true)
    Optional<Articulo> findByNameIsActive(String name);

    @Query(value = "SELECT * FROM articulos a WHERE a.nombre LIKE %:query%", nativeQuery = true)
    List<Articulo> findByParam(@Param("query") String query);

    @Query(value = "SELECT DISTINCT a.* FROM articulos a INNER JOIN categorias c ON a.categoria_id = :categoriaQueryId", nativeQuery = true)
    List<Articulo> findByCategoria(@Param("categoriaQueryId") Long categoriaQueryId);
}
