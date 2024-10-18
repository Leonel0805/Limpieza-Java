package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;

import java.util.List;

public record PedidoYDetallesDTO(

        PedidoDTO pedido,
        List<DetallePedidoDTO> detalles
) {
}
