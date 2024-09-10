package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;

public record AuthUpdateAdminResponseDTO(

        ListadoAdministradorDTO admin,
        String jwt,
        String message

) {
}
