package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.estadoPedidoDTO.EstadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import Proyecto_Limpieza.app.limpieza.services.EncargadoService;
import Proyecto_Limpieza.app.limpieza.services.PedidoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EncargadoService encargadoService;

    @GetMapping
    public ResponseEntity<?> findAll() {

        List<ListadoPedidoDTO> pedidosResponse = pedidoService.findAllNoDelete();
        return ResponseEntity.status(HttpStatus.OK).body(pedidosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        ListadoPedidoDTO pedidoDTO = pedidoService.findById(id);

        if (pedidoDTO == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "No se encontró ningún pedido con ese ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(pedidoDTO);
    }

//    POST
    @PostMapping
    public ResponseEntity<?> guardarPedido(@RequestBody @Valid PedidoDTO pedidoDTO) {

        ListadoPedidoDTO pedidoResponse = pedidoService.guardarPedido(pedidoDTO);
        if (pedidoResponse == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "Error al crear pedido, Encargado no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido creado correctamente!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedidoEstado(@PathVariable Long id, @RequestBody @Valid EstadoPedidoDTO estadoPedidoDTO) {

        ListadoPedidoDTO pedidoResponse = pedidoService.actualizarPedidoById(id, estadoPedidoDTO);

        if (pedidoResponse == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "Error al actualizar pedido, pedido no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido actualizado correctamente!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        ListadoPedidoDTO pedidoResponse = pedidoService.deleteById(id);
        APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido 'ELIMINADO' correctamente!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
