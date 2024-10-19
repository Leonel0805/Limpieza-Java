package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.ArticuloDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloDAOImpl persistencia;


    @Autowired
    private UserDetailServiceImpl userDetailService;

    public List<Articulo> findAll() {
        return persistencia.findAll();
    }

    public List<Articulo> findAllOrderStock() {
        return persistencia.findAllOrderStock();
    }

    public Articulo findById(Long id){

        Optional<Articulo> articuloOptional = persistencia.findById(id);

        if (articuloOptional.isEmpty()) {
            throw new RuntimeException("Articulo no encontrado");
        }

        Articulo articulo = articuloOptional.get();
        return articulo;

    }

//    este no seria necesario, en caso de no estar con stock manipulamos el frontend
    public Articulo findByIdAndStock(Long id) {

        Optional<Articulo> articuloOptional = persistencia.findByIdAndStock(id);

        if (articuloOptional.isEmpty()) {
            throw new RuntimeException("Articulo no encontrado o está sin stock");
        }

        Articulo articulo = articuloOptional.get();
        return articulo;
    }

    public Articulo findByNameAndStock(String name) {

        Optional<Articulo> articuloOptional = persistencia.findByNameAndStock(name);

        if (articuloOptional.isEmpty()) {
            throw new RuntimeException("Articulo no encontrado o está sin stock");
        }

        return articuloOptional.get();
    }

    //    Buscar articulos por nombre enviado por parametro
    public List<Articulo> findByParam(String query) {

        String newQuery = query.toLowerCase();
        List<Articulo> articulosSearch = persistencia.findByParam(newQuery);

        return articulosSearch;
    }

    public void guardarArticulo(Articulo articulo) {
        persistencia.guardarArticulo(articulo);
    }

    public Articulo crearArticulo(ArticuloDTO articuloDTO) {

        Optional<Articulo> existingArticulo = persistencia.findByNameAndStock(articuloDTO.nombre());

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

        this.actualizarValores(articuloDTO, articulo);
        this.guardarArticulo(articulo);

        return articulo;
    }

    public Articulo eliminarArticulo(Long id) {

        Articulo articulo = this.findByIdAndStock(id);

        articulo.setStock(0);
        articulo.setSin_stock(Boolean.TRUE);

        this.guardarArticulo(articulo);

        return articulo;
    }

    public void actualizarValores(ArticuloDTO articuloDTO, Articulo articulo) {

        articulo.setNombre(articuloDTO.nombre());
        articulo.setDescripcion(articuloDTO.descripcion());
        if (articuloDTO.stock() > 0 && articulo.getSin_stock()){
            articulo.setSin_stock(false);
        }
        articulo.setStock(articuloDTO.stock());
        articulo.setPrecio(articuloDTO.precio());
    }


    public Articulo actualizarArticuloStock(Articulo articulo, Integer cantidad) {

        Integer nuevoStock = articulo.getStock() - cantidad;

        if (nuevoStock < 0) {
            throw new RuntimeException("No hay cantidad disponible");

        } else if (nuevoStock == 0) {
            articulo.setSin_stock(true);
        }

        articulo.setStock(nuevoStock);
        this.guardarArticulo(articulo);

        return articulo;
    }

}
