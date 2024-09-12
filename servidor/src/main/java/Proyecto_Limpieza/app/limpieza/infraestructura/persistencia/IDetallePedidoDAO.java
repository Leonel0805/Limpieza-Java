package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;

public interface IDetallePedidoDAO {

    void save(DetallePedido detallePedido);

    void deleteById(Long id);

}
