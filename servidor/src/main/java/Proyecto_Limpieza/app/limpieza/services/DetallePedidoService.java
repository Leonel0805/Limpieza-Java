package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.DetallePedidoDAOImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

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
        Articulo articulo = articuloService.findByNameIsActive(strinArticulo);

        Articulo articuloStockActualizado = articuloService.actualizarArticuloStock(articulo, detallePedidoDTO.cantidad());

        DetallePedido detallePedido = new DetallePedido(detallePedidoDTO.cantidad(), articuloStockActualizado);

        return detallePedido;
    }

    public void eliminarDetallePedido(Long id) {
        persistencia.deleteById(id);
    }


    public void eliminarDetallesDelPedido(Pedido pedido) {

        List<DetallePedido> detallesAEliminar = new ArrayList<>(pedido.getDetallePedidos());

        for (DetallePedido detallePedido : detallesAEliminar) {

            Articulo articulo = detallePedido.getArticulo();
            Integer cantidad = detallePedido.getCantidad();

            articulo.setStock(articulo.getStock() + cantidad);
            articulo.setIs_active(false);
            articuloService.guardarArticulo(articulo);

            // Eliminar el detallePedido de la lista original
            pedido.getDetallePedidos().remove(detallePedido); //eliminamos de la lista
            this.eliminarDetallePedido(detallePedido.getId());
        }
    }


}
