package Proyecto_Limpieza.app.limpieza.domain.models.articulo;

import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

    @Column(unique = true)
    private String nombre;
    private String descripcion;

    private String imgUrl;
    private int stock;
    private float precio;
    private Boolean is_active;
    //private image imagen;

    @OneToMany(mappedBy = "articulo")
    private Set<DetallePedido> detallesPedido;

    public Articulo(ArticuloDTO articuloDTO) {
        this.nombre = articuloDTO.nombre();
        this.descripcion = articuloDTO.descripcion();
        this.stock = articuloDTO.stock();
        this.precio = articuloDTO.precio();
        this.is_active = Boolean.TRUE;
    }

}
