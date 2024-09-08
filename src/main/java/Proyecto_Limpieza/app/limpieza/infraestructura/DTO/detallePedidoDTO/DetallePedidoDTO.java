package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DetallePedidoDTO(

        @NotNull
        Integer cantidad,
        @NotBlank
        String articulo_name
) {
}
