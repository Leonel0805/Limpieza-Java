package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.ListarDetallePedidoDTO;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ListadoPedidoDTO(

     Long id,

     EstadoPedido estado,

     LocalDateTime fecha_creacion,

     EncargadoPedidoDTO encargado,

     List<ListarDetallePedidoDTO> detalle_pedidos

) {
    public ListadoPedidoDTO(Pedido pedido) {
        this(pedido.getId(),
                pedido.getEstado(),
                pedido.getFecha_creacion(),
                new EncargadoPedidoDTO(pedido.getEncargado()),

                pedido.getDetallePedidos() != null ?
                        pedido.getDetallePedidos()
                                .stream()
                                .map(detallePedido -> new ListarDetallePedidoDTO(detallePedido))
                                .collect(Collectors.toList())
                        : Collections.emptyList());
    }

}
