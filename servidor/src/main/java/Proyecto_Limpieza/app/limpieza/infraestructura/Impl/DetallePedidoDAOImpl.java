package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedidoRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IDetallePedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoDAOImpl implements IDetallePedidoDAO {

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Override
    public void save(DetallePedido detallePedido) {
        detallePedidoRepository.save(detallePedido);
    }

    @Override
    public void deleteById(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}
