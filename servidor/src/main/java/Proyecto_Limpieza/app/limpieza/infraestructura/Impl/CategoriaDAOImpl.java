package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import Proyecto_Limpieza.app.limpieza.domain.models.categoria.CategoriaRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.ICategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoriaDAOImpl implements ICategoriaDAO {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> findByName(String name) {
        return categoriaRepository.findByName(name);
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarArticulo(Long id) {
        categoriaRepository.deleteById(id);
    }
}
