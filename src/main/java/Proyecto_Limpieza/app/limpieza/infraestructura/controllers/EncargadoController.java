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
@RequestMapping("/api/encargados")
public class EncargadoController {

    @Autowired
    private EncargadoService encargadoService;

//    GET All
    @GetMapping
    public ResponseEntity<?> findAll() {

        List<ListadoEncargadoDTO> encargadosDTOs = encargadoService.findAllIsEnabled().stream()
                .map(encargado -> new ListadoEncargadoDTO(encargado))
                .collect(Collectors.toList());;

        return ResponseEntity.status(HttpStatus.OK).body(encargadosDTOs);
    }

//    Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Encargado encargado = encargadoService.findByIdAndIsEnabled(id);
            ListadoEncargadoDTO encargadoDTO = new ListadoEncargadoDTO(encargado);
            return ResponseEntity.status(HttpStatus.OK).body(encargadoDTO);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

//    POST
    @PostMapping
    public ResponseEntity<?> guardarEncargado(@RequestBody @Valid EncargadoDTO encargadoDTO) {

        try {
            Encargado encargado = encargadoService.guardarEncargado(encargadoDTO);

            ListadoEncargadoDTO encargadoResponse = new ListadoEncargadoDTO(encargado);
            APIResponseDTO response = new APIResponseDTO(encargadoResponse, "Encargado creado correctamente!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    //    PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEncargado(@PathVariable Long id, @RequestBody EncargadoDTO encargadoDTO) {

        try {
            Encargado encargado = encargadoService.actualizarEncargado(id, encargadoDTO);

            ListadoEncargadoDTO encargadoResponse = new ListadoEncargadoDTO(encargado);
            APIResponseDTO response = new APIResponseDTO(encargadoResponse, "Encargado actualizado correctamente!");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    //    DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEncargado(@PathVariable Long id) {

        try {
            Encargado encargado = encargadoService.deleteById(id);
            ListadoEncargadoDTO encargadoResponse = new ListadoEncargadoDTO(encargado);
            APIResponseDTO response = new APIResponseDTO(encargadoResponse, "Encargado 'eliminado' correctamente!");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {

            APIResponseDTO response = new APIResponseDTO("Error -" + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }


    }
}
