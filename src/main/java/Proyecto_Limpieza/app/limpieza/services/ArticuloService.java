package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.ArticuloDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IArticuloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloDAOImpl persistencia;


    public List<Articulo> findAll() {
        return persistencia.findAll();
    }


    public Articulo findById(Long id) {
        Optional<Articulo> articuloOptional = persistencia.findById(id);

        if (articuloOptional.isEmpty()) {
            return null;
        }

        Articulo articulo = articuloOptional.get();
        return articulo;
    }


    public void guardarArticulo(Articulo articulo) {
        persistencia.guardarArticulo(articulo);
    }

    public Articulo crearArticulo(ArticuloDTO articuloDTO) {

        Optional<Articulo> existingArticulo = persistencia.findByNombre(articuloDTO.nombre());

        if (existingArticulo.isPresent()) {
            throw new RuntimeException("El artículo con nombre " + articuloDTO.nombre() + " ya existe");
        }

        // Crear y guardar el nuevo artículo
        Articulo articulo = new Articulo(articuloDTO);
        persistencia.guardarArticulo(articulo);

        return articulo;
    }

    public Articulo actualizarArticulo(Long id, ArticuloDTO articuloDTO) {

        Articulo articulo = this.findById(id);

        if (articulo == null) {
            return null;
        }

        this.actualizarValores(articuloDTO, articulo);
        this.guardarArticulo(articulo);

        return articulo;
    }

    public Articulo eliminarArticulo(Long id) {

        Articulo articulo = this.findById(id);

        if (articulo == null) {
            return null;
        }

        articulo.setStock(0);
        articulo.setSin_stock(Boolean.TRUE);
        this.guardarArticulo(articulo);

        return articulo;
    }

    public void actualizarValores(ArticuloDTO articuloDTO, Articulo articulo) {

        articulo.setNombre(articuloDTO.nombre());
        articulo.setDescripcion(articuloDTO.descripcion());
        articulo.setStock(articuloDTO.stock());
        articulo.setPrecio(articuloDTO.precio());
    }
}
