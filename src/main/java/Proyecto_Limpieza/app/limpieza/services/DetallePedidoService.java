package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.DetallePedidoDAOImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DetallePedidoService {

    @Autowired
    DetallePedidoDAOImpl persistencia;

    @Autowired
    ArticuloService articuloService;

    public DetallePedido crearDetallePedido(DetallePedidoDTO detallePedidoDTO) {

        if (detallePedidoDTO.articulo_name() == null) {
            throw new RuntimeException("Nombre de articulo no valido");
        }

        String strinArticulo = detallePedidoDTO.articulo_name();
        Articulo articulo = articuloService.findByNameAndStock(strinArticulo);

        Articulo articuloStockActualizado = articuloService.actualizarArticuloStock(articulo, detallePedidoDTO.cantidad());

        DetallePedido detallePedido = new DetallePedido(detallePedidoDTO.cantidad(), articuloStockActualizado);

        persistencia.save(detallePedido);

        return detallePedido;
    }
}
