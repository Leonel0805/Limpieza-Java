package Proyecto_Limpieza.app.limpieza.domain.models.pedido;


import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private LocalDateTime fecha_creacion = LocalDateTime.now() ;

    @ManyToOne
    private Encargado encargado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detallePedidos = new ArrayList<>();

    public Pedido(PedidoDTO pedidoDTO, Encargado encargado) {

        this.estado = pedidoDTO.estado() == null ? EstadoPedido.PENDIENTE : pedidoDTO.estado();
        this.encargado = encargado;
    }

    public void addDetallePedido(DetallePedido detallePedido) {
        detallePedido.setPedido(this);
        this.detallePedidos.add(detallePedido);
    }

}
