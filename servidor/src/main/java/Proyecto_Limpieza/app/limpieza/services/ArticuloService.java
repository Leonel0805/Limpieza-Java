package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.ArticuloDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloDAOImpl persistencia;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UserDetailServiceImpl userDetailService;



    public List<Articulo> findAll() {
        return persistencia.findAll();
    }

    public List<Articulo> findAllIsActiveAndOrderStock() {
        return persistencia.findAllIsActiveAndOrderStock();
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
    public Articulo findByIdIsActive(Long id) {

        Optional<Articulo> articuloOptional = persistencia.findByIdIsActive(id);

        if (articuloOptional.isEmpty()) {
            throw new RuntimeException("Articulo no encontrado o no disponible");
        }

        Articulo articulo = articuloOptional.get();
        return articulo;
    }

    public Articulo findByNameIsActive(String name) {

        Optional<Articulo> articuloOptional = persistencia.findByNameIsActive(name);

        if (articuloOptional.isEmpty()) {
            throw new RuntimeException("Articulo no encontrado o está sin stock");
        }

        return articuloOptional.get();
    }

    //    Buscar articulos por nombre enviado por parametro
    public List<Articulo> findByParam(String query) {

        String newQuery = query.toLowerCase();
        List<Articulo> articulosSearch;

        articulosSearch = persistencia.findByParam(newQuery);

        if (articulosSearch.isEmpty()) {
            Categoria categoria = categoriaService.findByNameParam(newQuery);

            if (categoria != null) {
                articulosSearch = persistencia.findByCategoria(categoria.getId());
            }
        }

        return articulosSearch;
    }

    public void guardarArticulo(Articulo articulo) {
        persistencia.guardarArticulo(articulo);
    }

    public Articulo crearArticulo(ArticuloDTO articuloDTO, MultipartFile file) {

        Optional<Articulo> existingArticulo = persistencia.findByNameIsActive(articuloDTO.nombre());

        if (existingArticulo.isPresent()) {
            throw new RuntimeException("El artículo con nombre " + articuloDTO.nombre() + " ya existe");
        }

        // Crear y guardar el nuevo artículo
        Articulo articulo = new Articulo(articuloDTO);

//        cargamos y retornamos un string para el archivo
        try {
            String imageString = cloudinaryService.cargarImagen(file);
            articulo.setImgUrl(imageString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            Categoria categoria = categoriaService.findByName(articuloDTO.categoria().name());
            categoria.addArticulo(articulo);
            persistencia.guardarArticulo(articulo);
            return articulo;

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public Articulo actualizarArticulo(Long id, ArticuloDTO articuloDTO) {

        Articulo articulo = this.findById(id);

        this.actualizarValores(articuloDTO, articulo);
        this.guardarArticulo(articulo);

        return articulo;
    }

    public Articulo eliminarArticulo(Long id) {

        Articulo articulo = this.findByIdIsActive(id);

//        articulo.setStock(0);
        articulo.setIs_active(Boolean.FALSE);

        this.guardarArticulo(articulo);

        return articulo;
    }

    public void actualizarValores(ArticuloDTO articuloDTO, Articulo articulo) {

        articulo.setNombre(articuloDTO.nombre());
        articulo.setDescripcion(articuloDTO.descripcion());
        if (articuloDTO.stock() > 0){
            articulo.setStock(articuloDTO.stock());
        }
        articulo.setPrecio(articuloDTO.precio());
    }


    public Articulo actualizarArticuloStock(Articulo articulo, Integer cantidad) {

        Integer nuevoStock = articulo.getStock() - cantidad;

        if (nuevoStock < 0) {
            throw new RuntimeException("No hay cantidad disponible");

        }

        articulo.setStock(nuevoStock);
        this.guardarArticulo(articulo);

        return articulo;
    }

}
