package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;

public record ListadoCategoriaDTO(

        Long id,
        String name
) {
    public ListadoCategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getName());
    }
}
