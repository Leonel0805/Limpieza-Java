package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public record ListadoPedidoDTO(


     EstadoPedido estado,

     LocalDateTime fecha_creacion,

     Encargado encargado

) {
    public ListadoPedidoDTO(Pedido pedido) {
        this(pedido.getEstado(), pedido.getFecha_creacion(), pedido.getEncargado());
    }

}
