package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

public record AuthResponseDTO(
        String username,
        String message,
        String jwt,
        Boolean status
) {
}
