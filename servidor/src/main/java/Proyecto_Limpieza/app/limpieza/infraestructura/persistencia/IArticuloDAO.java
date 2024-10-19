package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface IArticuloDAO {


    List<Articulo> findAll();
    List<Articulo> findAllOrderStock();

    List<Articulo> findByParam(String query);

    Optional<Articulo> findById(Long id);

    Optional<Articulo> findByNameAndStock(String name);

    Optional<Articulo> findByIdAndStock(Long id);

    void guardarArticulo(Articulo articulo);

    void eliminarArticulo(Long id);
}
