package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.ArticuloDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IArticuloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    public Articulo findByIdAndStock(Long id) {

        String username = userDetailService.obtenerUsuarioAutenticado();
        System.out.println(username);

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

        Articulo articulo = this.findByIdAndStock(id);

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