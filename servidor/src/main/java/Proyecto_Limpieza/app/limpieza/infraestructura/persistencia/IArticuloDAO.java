package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface IArticuloDAO {


    List<Articulo> findAll();
    List<Articulo> findAllIsActiveAndOrderStock();

    List<Articulo> findByParam(String query);

    List<Articulo> findByCategoria(Long id);

    Optional<Articulo> findById(Long id);

    Optional<Articulo> findByNameIsActive(String name);

    Optional<Articulo> findByIdIsActive(Long id);

    void guardarArticulo(Articulo articulo);

    void eliminarArticulo(Long id);
}
