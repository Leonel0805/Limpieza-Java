package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;

public record AuthUpdateDTO(

        String jwt,
        UserEntity user
) {
}
