package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import java.time.LocalDateTime;

public record PedidoDTO(

        EstadoPedido estado, //si hay un estado asignarlo, sino poner default

        String nombre_encargado
) {
}
