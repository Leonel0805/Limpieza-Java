package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO.CategoriaDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.CategoriaDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaDAOImpl persistencia;

    public List<Categoria> findAll() {
        return persistencia.findAll();
    }

    public Categoria findById(Long id) {

        Optional<Categoria> categoriaOptional = persistencia.findById(id);

        if (categoriaOptional.isEmpty()) {
            throw new RuntimeException("No se encontró ninguna categoría con ese id");
        }

        return categoriaOptional.get();
    }

    public Categoria findByName(String name) {

        Optional<Categoria> categoriaFind = persistencia.findByName(name);

        if (categoriaFind.isEmpty()) {
            throw new RuntimeException("Categoria no encontrada");
        }

        return categoriaFind.get();
    }

    public Categoria findByNameParam(String query) {

        Optional<Categoria> categoriaFind = persistencia.findByNameParam(query);

        if (categoriaFind.isEmpty()) {
            return null;
        }

        return categoriaFind.get();
    }

    public Categoria crearCategoria(CategoriaDTO categoriaDTO) {

        String categoriaName = categoriaDTO.name();

        Optional<Categoria> categoriaExist = persistencia.findByName(categoriaName);

        if (categoriaExist.isPresent()) {
            throw new RuntimeException("Categoria ya existe");
        }

        Categoria categoria = new Categoria(categoriaDTO);
        persistencia.guardarCategoria(categoria);

        return categoria;
    }

    public Categoria updateCategoria(Long id, CategoriaDTO categoriaDTO){

        Categoria categoriaFind =  this.findById(id);

        if (categoriaFind != null) {

            categoriaFind.setName(categoriaDTO.name());

            persistencia.guardarCategoria(categoriaFind);
        }

        return categoriaFind;
    }

    //    eliminar categoria
    public void deleteCategoria(Long id) {

        persistencia.eliminarArticulo(id);
    }


}

