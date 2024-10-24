package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaDAO {

    List<Categoria> findAll();
    Optional<Categoria> findById(Long id);

    Optional<Categoria> findByName(String name);

    Optional<Categoria> findByNameParam(String query);

    void guardarCategoria(Categoria categoria);

    void eliminarArticulo(Long id);
}
