package Proyecto_Limpieza.app.limpieza.domain.models.administrador;


import Proyecto_Limpieza.app.limpieza.domain.models.user.User;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.DatosAdministrador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "administradores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean is_admin = Boolean.TRUE;

    public Administrador(DatosAdministrador datosadministrador) {
        super(datosadministrador.name(), datosadministrador.email(), datosadministrador.password(), Boolean.TRUE);
    }
}
