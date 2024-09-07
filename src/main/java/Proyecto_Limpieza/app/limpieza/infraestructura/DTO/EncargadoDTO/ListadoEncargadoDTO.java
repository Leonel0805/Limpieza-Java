package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO.EdificioDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ListadoEncargadoDTO (

        Long id,
        String DNI,
        String name,
        String apellido,
        EdificioDTO edificio,
        List<ListadoPedidoDTO> pedidos
){
    public ListadoEncargadoDTO(Encargado encargado) {
        this(
                encargado.getId(),
                encargado.getDNI(),
                encargado.getUsername(),
                encargado.getApellido(),
                encargado.getEdificio() != null ? new EdificioDTO(encargado.getEdificio()) : null,
                encargado.getPedidos() != null ?
                        encargado.getPedidos().stream()
                                .map(pedido -> new ListadoPedidoDTO(pedido))
                                .collect(Collectors.toList()) : Collections.emptyList()
        );
    }
}