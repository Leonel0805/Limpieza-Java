package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;

import java.util.List;

public record EncargadoPedidoDTO(

        Long id,
        String DNI,
        String name,
        String apellido
) {
    public EncargadoPedidoDTO(Encargado encargado) {
        this(encargado.getId(), encargado.getDNI(), encargado.getName(), encargado.getApellido());
    }
}
