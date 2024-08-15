package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.ArticuloDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IArticuloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService implements IArticuloDAO {

    @Autowired
    private IArticuloDAO persistencia;

    @Override
    public List<Articulo> findAll() {
        return persistencia.findAll();
    }

    @Override
    public Optional<Articulo> findById(Long id) {
        return persistencia.findById(id);
    }

    @Override
    public void guardarArticulo(Articulo articulo) {
        persistencia.guardarArticulo(articulo);
    }

    @Override
    public void eliminarArticulo(Long id) {
        persistencia.eliminarArticulo(id);
    }
}
