package Proyecto_Limpieza.app.limpieza.domain.models.detallePedido;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "detalle_Pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne
    private Articulo articulo;
}
