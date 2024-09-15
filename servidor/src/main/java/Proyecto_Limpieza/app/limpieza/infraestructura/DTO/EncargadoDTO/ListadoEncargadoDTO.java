package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RoleNameDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO.EdificioDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoEncargadoDTO;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ListadoEncargadoDTO (

        Long id,
        String DNI,
        String username,
        String apellido,
        EdificioDTO edificio,
        Set<RoleNameDTO> roles,
        List<PedidoEncargadoDTO> pedidos
){
    public ListadoEncargadoDTO(Encargado encargado) {
        this(
                encargado.getId(),
                encargado.getDNI(),
                encargado.getUsername(),
                encargado.getApellido(),
                encargado.getEdificio() != null ? new EdificioDTO(encargado.getEdificio()) : null,
                encargado.getRoles().stream()
                        .map(roleEntity -> new RoleNameDTO(roleEntity))
                        .collect(Collectors.toSet()),
                encargado.getPedidos() != null ?
                        encargado.getPedidos().stream()
                                .map(pedido -> new PedidoEncargadoDTO(pedido))
                                .collect(Collectors.toList()) : Collections.emptyList()
        );
    }
}