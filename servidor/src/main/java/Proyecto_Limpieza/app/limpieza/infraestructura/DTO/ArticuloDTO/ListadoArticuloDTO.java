package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO.CategoriaDTO;

public record ListadoArticuloDTO (

        Long id,
        String nombre,
        String descripcion,
        int stock,
        float precio,
        String imageUrl,
        CategoriaDTO categoria,
        Boolean is_active
){
    public ListadoArticuloDTO(Articulo articulo) {
        this(articulo.getId(), articulo.getNombre(), articulo.getDescripcion(), articulo.getStock(), articulo.getPrecio(),
                articulo.getImgUrl(),
                articulo.getCategoria() != null ? new CategoriaDTO(articulo.getCategoria().getName()) : null,
                articulo.getIs_active());

    }

}
