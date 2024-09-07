package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.perfilDTO;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;

public record PerfilUpdateDTO(

        AdministradorDTO administradorDTO,
        EncargadoDTO encargadoDTO
) {
}
