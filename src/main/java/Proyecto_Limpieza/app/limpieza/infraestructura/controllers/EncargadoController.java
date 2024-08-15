package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.services.EncargadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/encargado")
public class EncargadoController {

    @Autowired
    private EncargadoService encargadoService;

    @GetMapping
    public ResponseEntity<List<ListadoEncargadoDTO>> findAll() {

        List<ListadoEncargadoDTO> encargadosDTOs = encargadoService.findAll().stream()
                .map(encargado -> new ListadoEncargadoDTO(encargado))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(encargadosDTOs);
    }
}
