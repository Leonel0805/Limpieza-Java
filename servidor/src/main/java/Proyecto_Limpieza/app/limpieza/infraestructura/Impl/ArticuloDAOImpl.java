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
    public Optional<Articulo> findById(Long id) {
        return articuloRepository.findById(id);
    }

    @Override
    public Optional<Articulo> findByNameAndStock(String name) {
        return articuloRepository.findByNameAndStock(name);
    }

    @Override
    public Optional<Articulo> findByIdAndStock(Long id) {
        return articuloRepository.findByIdAndStock(id);
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
