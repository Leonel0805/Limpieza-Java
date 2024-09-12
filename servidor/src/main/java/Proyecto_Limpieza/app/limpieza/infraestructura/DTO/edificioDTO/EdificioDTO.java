package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EdificioDTO(

        @NotBlank
        String calle,
        @NotNull
        int numero

) {
        public EdificioDTO(Edificio edificio) {
                this(edificio.getCalle(), edificio.getNumero());
        }
}
