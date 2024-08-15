package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edifico;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;

import java.util.List;

public record ListadoEncargadoDTO (

        Long id,
        String DNI,
        String apellido,
        Edifico edifico,
        List<Pedido>pedidos
){
    public ListadoEncargadoDTO(Encargado encargado) {
        this(encargado.getId(), encargado.getDNI(), encargado.getApellido(), encargado.getEdifico(), encargado.getPedidos());
    }
}
