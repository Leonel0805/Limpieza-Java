package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedidoRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.ListarDetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/detalle_pedidos")
@PreAuthorize("permitAll()")
public class DetallePedidoControler {


    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<?> findAll() {

        List<ListarDetallePedidoDTO> detallePedidoResponse = detallePedidoRepository.findAll().stream()
                .map(detallePedido -> new ListarDetallePedidoDTO(detallePedido))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(detallePedidoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("error"));

        ListarDetallePedidoDTO response = new ListarDetallePedidoDTO(detallePedido);
        return ResponseEntity.ok(response);
    }

//    este POST no se usa, se usa el POST en pedido
    @PostMapping
    public ResponseEntity<?> crearDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {

        try {
            DetallePedido detallePedido = detallePedidoService.crearDetallePedido(detallePedidoDTO);
            ListarDetallePedidoDTO detallePedidoResponse = new ListarDetallePedidoDTO(detallePedido);

            APIResponseDTO response = new APIResponseDTO(detallePedidoResponse, "Detalle del Pedido creado correctamente!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {

            APIResponseDTO response = new APIResponseDTO("Error - Bad Request", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
