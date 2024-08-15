package Proyecto_Limpieza.app.limpieza.domain.models.edificio;

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
public class Edifico {

    private String calle;
    private int número;

}
