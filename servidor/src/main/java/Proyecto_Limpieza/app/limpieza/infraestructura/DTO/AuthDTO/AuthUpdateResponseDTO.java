package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.userDTO.ResponseUpdateUserGenericDTO;

public record AuthUpdateResponseDTO(

        ResponseUpdateUserGenericDTO user,
        String jwt,
        String message

) {
}
