package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoDAO {

    List<Pedido> findAll();

    List<Pedido> findAllNoDelete();
    Optional<Pedido> findById(Long id);

    void guardarPedido(Pedido pedido);

    void deleteById(Long id);
}
