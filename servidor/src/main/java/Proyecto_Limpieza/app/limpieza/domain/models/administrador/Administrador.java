package Proyecto_Limpieza.app.limpieza.domain.models.administrador;


import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "administradores")
@Getter
@Setter
@AllArgsConstructor
public class Administrador extends UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    public Administrador(AdministradorDTO datosadministrador, String hashPassword) {
        super(datosadministrador.username(), datosadministrador.email(), hashPassword);

    }

    public Administrador(String username, String email, String hashPassword) {
        super(username, email, hashPassword);
    }
}
