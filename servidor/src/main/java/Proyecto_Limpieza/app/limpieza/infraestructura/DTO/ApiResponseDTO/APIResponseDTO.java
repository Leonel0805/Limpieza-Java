package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO;

public record APIResponseDTO<T>(

        T data,
        String message
) {
}
