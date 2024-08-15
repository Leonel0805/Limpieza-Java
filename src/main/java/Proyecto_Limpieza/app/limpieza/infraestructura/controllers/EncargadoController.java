package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.services.EncargadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/encargado")
public class EncargadoController {

    @Autowired
    private EncargadoService encargadoService;

//    GET All
    @GetMapping
    public ResponseEntity<List<ListadoEncargadoDTO>> findAll() {

        List<ListadoEncargadoDTO> encargadosDTOs = encargadoService.findAll().stream()
                .map(encargado -> new ListadoEncargadoDTO(encargado))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(encargadosDTOs);
    }

//    Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Encargado> encargadoOptional = encargadoService.findById(id);

        if (encargadoOptional.isEmpty()) {
            APIResponseDTO response = new APIResponseDTO("Error - Bad Request", "No se encontró ningún encargado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Encargado encargado = encargadoOptional.get();
        ListadoEncargadoDTO encargadoDTO = new ListadoEncargadoDTO(encargado);

        return ResponseEntity.status(HttpStatus.OK).body(encargadoDTO);
    }

//    POST
    @PostMapping
    public ResponseEntity<?> guardarEncargado(@RequestBody @Valid EncargadoDTO encargadoDTO) {

        Encargado encargado = new Encargado(encargadoDTO);

        encargadoService.guardarEncargado(encargado);

        ListadoEncargadoDTO encargadoResponse = new ListadoEncargadoDTO(encargado);
        APIResponseDTO response = new APIResponseDTO(encargadoResponse, "Encargado creado correctamente!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    PUT
}
