package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;

public record ListadoArticuloDTO (

        Long id,
        String nombre,
        String descripcion,
        int stock,
        float precio,
        String imageUrl,
        Boolean is_active
){
    public ListadoArticuloDTO(Articulo articulo) {
        this(articulo.getId(), articulo.getNombre(), articulo.getDescripcion(), articulo.getStock(), articulo.getPrecio(),
                articulo.getImgUrl(), articulo.getIs_active());

    }

}
