package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PedidoDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IPedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService{

    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;

    @Autowired
    private EncargadoService encargadoService;

    public List<ListadoPedidoDTO> findAll() {

        List<ListadoPedidoDTO> pedidosResponse = pedidoDAOImpl.findAll().stream()
                .map(pedido -> new ListadoPedidoDTO(pedido))
                .collect(Collectors.toList());

        return pedidosResponse;
    }


    public ListadoPedidoDTO findById(Long id) {

        Optional<Pedido> pedidoOptional = pedidoDAOImpl.findById(id);

        if (pedidoOptional.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidoOptional.get();

        ListadoPedidoDTO pedidoDTO = new ListadoPedidoDTO(pedido);
        return  pedidoDTO;
    }


    public ListadoPedidoDTO guardarPedido(PedidoDTO pedidoDTO) {

        Optional<Encargado> encargadoOptional = encargadoService.findById(pedidoDTO.encargado_id());

        System.out.println(pedidoDTO.encargado_id());
        System.out.println(encargadoOptional);
        if (encargadoOptional.isEmpty()) {
            return null;
        }

        Encargado encargado = encargadoOptional.get();
        Pedido pedido = new Pedido(pedidoDTO, encargado);
        pedidoDAOImpl.guardarPedido(pedido);

        return new ListadoPedidoDTO(pedido);
    }


    public void deleteById(Long id) {

    }
}
