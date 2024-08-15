package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;

public record ListadoArticuloDTO (

        Long id,
        String nombre,
        String descripcion,
        int stock,
        float precio
        //private image imagen;
){
    public ListadoArticuloDTO(Articulo articulo) {
        this(articulo.getId(), articulo.getNombre(), articulo.getDescripcion(), articulo.getStock(), articulo.getPrecio());

    }

}
