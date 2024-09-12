package Proyecto_Limpieza.app.limpieza.domain.models.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "SELECT * FROM pedidos WHERE estado != 'ELIMINADO'", nativeQuery = true)
    List<Pedido> findAllEstadoNoCancelado();
}
