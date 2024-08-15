package Proyecto_Limpieza.app.limpieza.domain.models.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter //Lombock
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass //notacion para indicar que es una clasePadre y herede los campos a los hijos
public class User {

    private String name;
    private String email;
    private String password;
    private Boolean isActive = Boolean.TRUE;

}
