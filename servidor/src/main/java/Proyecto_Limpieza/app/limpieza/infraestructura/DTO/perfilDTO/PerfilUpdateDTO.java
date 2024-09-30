package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.perfilDTO;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoUpdateDTO;

public record PerfilUpdateDTO(

        AdministradorUpdateDTO administrador,
        EncargadoUpdateDTO encargado
) {
}
