package Proyecto_Limpieza.app.limpieza.domain.models.pedido;


import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    private LocalDateTime fecha_creacion = LocalDateTime.now() ;

    @ManyToOne
    private Encargado encargado;

    public Pedido(PedidoDTO pedidoDTO, Encargado encargado) {
        this.estado = pedidoDTO.estado();
        this.encargado = encargado;
    }
}
