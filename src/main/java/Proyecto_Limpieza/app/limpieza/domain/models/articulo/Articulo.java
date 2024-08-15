package Proyecto_Limpieza.app.limpieza.domain.models.articulo;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articulos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private int stock;
    private float precio;
    private Boolean sin_stock;
    //private image imagen;


    public Articulo(ArticuloDTO articuloDTO) {
        this.nombre = articuloDTO.nombre();
        this.descripcion = articuloDTO.descripcion();
        this.stock = articuloDTO.stock();
        this.precio = articuloDTO.precio();
        this.sin_stock = Boolean.FALSE;
    }

}
