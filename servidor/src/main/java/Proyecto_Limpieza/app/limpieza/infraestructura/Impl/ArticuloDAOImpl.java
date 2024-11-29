package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.articulo.ArticuloRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IArticuloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArticuloDAOImpl implements IArticuloDAO {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public List<Articulo> findAll() {
        return articuloRepository.findAll();
    }

    @Override
    public List<Articulo> findAllWithFilters(Long categoriaId,Double precio, Boolean isActive,String nombre) {
        return articuloRepository.findAllWithFilters(categoriaId, precio, isActive, nombre);
    }

    @Override
    public List<Articulo> findAllIsActiveAndOrderStock() {
        return articuloRepository.findAllIsActiveAndOrderStock();
    }

    @Override
    public List<Articulo> findByParam(String query) {
        return articuloRepository.findByParam(query);
    }

    @Override
    public List<Articulo> findByCategoria(Long id) {
        return articuloRepository.findByCategoria(id);
    }


    @Override
    public Optional<Articulo> findById(Long id) {
        return articuloRepository.findById(id);
    }

    @Override
    public Optional<Articulo> findByNameIsActive(String name) {
        return articuloRepository.findByNameIsActive(name);
    }

    @Override
    public Optional<Articulo> findByIdIsActive(Long id) {
        return articuloRepository.findByIdIsActive(id);
    }

    @Override
    public void guardarArticulo(Articulo articulo) {
        articuloRepository.save(articulo);
    }

    @Override
    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}
