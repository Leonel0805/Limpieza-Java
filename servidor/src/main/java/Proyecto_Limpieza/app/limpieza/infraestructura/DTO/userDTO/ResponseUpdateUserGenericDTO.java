package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.userDTO;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;

public record ResponseUpdateUserGenericDTO(

        Long id,
        String username,
        String email
) {
    public ResponseUpdateUserGenericDTO(UserEntity user) {
        this(user.getId(), user.getUsername(), user.getEmail());
    }
}
