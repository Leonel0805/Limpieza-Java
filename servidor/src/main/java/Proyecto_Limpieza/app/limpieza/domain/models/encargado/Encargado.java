package Proyecto_Limpieza.app.limpieza.domain.models.encargado;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Encargados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encargado extends UserEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String DNI;
    private String apellido;

    @Embedded //aca le indicamos que queremos sumar lo de la Clase Edificio, sin una nueva tabla
    private Edificio edificio;

    @OneToMany(mappedBy = "encargado", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;


    public Encargado(EncargadoDTO encargadoDTO, String hassPassword) {
        super(encargadoDTO.name(), encargadoDTO.email(), hassPassword);
        this.DNI = encargadoDTO.DNI();
        this.apellido = encargadoDTO.apellido();
        this.edificio = new Edificio(encargadoDTO.edificio());
    }

    public Encargado(String username, String email, String hassPassword) {
        super(username, email, hassPassword);
    }

}
