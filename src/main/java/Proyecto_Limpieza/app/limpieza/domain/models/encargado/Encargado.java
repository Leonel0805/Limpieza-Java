package Proyecto_Limpieza.app.limpieza.domain.models.encargado;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.domain.models.user.User;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.spec.ECField;
import java.util.List;

@Entity
@Table(name = "Encargados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encargado extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String DNI;
    private String apellido;

    @Embedded //aca le indicamos que queremos sumar lo de la Clase Edificio, sin una nueva tabla
    private Edificio edificio;

    @OneToMany
    private List<Pedido> pedidos;

    public Encargado(EncargadoDTO encargadoDTO) {
        super(encargadoDTO.name(), encargadoDTO.email(), encargadoDTO.password(), Boolean.TRUE);
        this.DNI = encargadoDTO.DNI();
        this.apellido = encargadoDTO.apellido();
        this.edificio = new Edificio(encargadoDTO.edificio());
        this.pedidos = encargadoDTO.pedidos();
    }
}
