package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PedidoDTO(

        @NotNull
        EstadoPedido estado,

        LocalDateTime fecha_creacion,

        @NotNull
        Long encargado_id

) {
}
