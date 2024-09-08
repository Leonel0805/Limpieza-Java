package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;


public record ListarDetallePedidoDTO(

        Long id,
        Integer cantidad,
        ListadoArticuloDTO articulo
) {
    public ListarDetallePedidoDTO(DetallePedido detallePedido) {
        this(detallePedido.getId(), detallePedido.getCantidad(), new ListadoArticuloDTO(detallePedido.getArticulo()));
    }
}
