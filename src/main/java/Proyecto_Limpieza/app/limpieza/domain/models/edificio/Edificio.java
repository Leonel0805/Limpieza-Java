package Proyecto_Limpieza.app.limpieza.domain.models.edificio;

import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO.EdificioDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Edificio {

    private String calle;
    private int numero;


    public Edificio(EdificioDTO edificioDTO) {
        this.calle = edificioDTO.calle();
        this.numero = edificioDTO.numero();
    }
}
