package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.CategoriaDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaDAOImpl persistencia;


    public Categoria findByName(String name) {

        Optional<Categoria> categoriaFind = persistencia.findByName(name);

        if (categoriaFind.isEmpty()) {
            throw new RuntimeException("Categoria no encontrada");
        }

        return categoriaFind.get();
    }
}
