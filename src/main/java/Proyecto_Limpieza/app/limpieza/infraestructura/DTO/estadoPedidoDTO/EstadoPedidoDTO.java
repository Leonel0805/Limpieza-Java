package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.estadoPedidoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;

import jakarta.validation.constraints.NotNull;

public record EstadoPedidoDTO(

        @NotNull(message = "El estado debe ser PENDIENTE, ENTREGADO O CANCELADO")
        EstadoPedido estado
) {
}
