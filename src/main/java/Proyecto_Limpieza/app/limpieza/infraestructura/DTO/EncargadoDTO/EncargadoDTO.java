package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO.EdificioDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record EncargadoDTO(

        @NotBlank
        String name,
        @NotBlank(message = "Debe haber un mail")
        String email,
        @NotBlank(message = "Password no puede estar vació") //no lanza el mensaje
        String password,

        @NotBlank
        String DNI,
        @NotBlank
        String apellido,

        EdificioDTO edificio,
        List<Pedido> pedidos
) {
}
