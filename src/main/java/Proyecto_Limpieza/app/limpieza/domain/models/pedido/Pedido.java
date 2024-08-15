package Proyecto_Limpieza.app.limpieza.domain.models.pedido;


import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean estado = Boolean.FALSE;

    private LocalDateTime fecha_creacion = LocalDateTime.now() ;

    @ManyToOne
    private Encargado encargado;

}
