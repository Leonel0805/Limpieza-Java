package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;
import Proyecto_Limpieza.app.limpieza.services.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

//    Get All admins
    @GetMapping
    public ResponseEntity<?> findAll() {

//        Convertimos a lista de DTO para mostrar los datos que queremos
        List<ListadoAdministradorDTO> list_administradores = administradorService.findAll();

        return ResponseEntity.ok(list_administradores);
    }

//    Get admin byid
    @GetMapping("/{id}")
    public ResponseEntity<ListadoAdministradorDTO> findById(@PathVariable Long id) {

        Optional<Administrador> adminOptional = administradorService.findById(id);

        if (adminOptional.isPresent()) {
            Administrador admin = adminOptional.get();
            ListadoAdministradorDTO adminReturn = new ListadoAdministradorDTO(admin);
            return ResponseEntity.ok(adminReturn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    POST admin
    @PostMapping
    public ResponseEntity guardarAdmin(@RequestBody @Valid AdministradorDTO administradorDTO) throws URISyntaxException {

        ListadoAdministradorDTO adminDTO = administradorService.crearAdmin(administradorDTO);

        if (adminDTO == null) {
            APIResponseDTO response = new APIResponseDTO(adminDTO, "No se pudo crear el admin.");
            return ResponseEntity.badRequest().body(response);
        }
        APIResponseDTO response = new APIResponseDTO(adminDTO, "Administrador creado correctamente!");

        return ResponseEntity.created(new URI("/api/administradores"))
                .body(response);
    }

//    PUT
    @PutMapping("/{id}")
    public ResponseEntity actualizarAdmin(@PathVariable Long id, @RequestBody @Valid AdministradorDTO datosAdministrador) {

//        Obtenemos el admin por id
        Optional<Administrador> adminOptional = administradorService.findById(id);

        if (adminOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

//        Obtenemos el objeto
        Administrador admin = adminOptional.get();

//        Seteamos sus nuevos valores
        admin.setUsername(datosAdministrador.name());
        admin.setEmail(datosAdministrador.email());
        admin.setPassword(datosAdministrador.password());

//        Guardamos en la database con los nuevos valores
        administradorService.guardarAdmin(admin);

//        Convertimos en DTO al admin a mostrar
        ListadoAdministradorDTO adminDTO = new ListadoAdministradorDTO(admin);

        APIResponseDTO response = new APIResponseDTO(adminDTO, "Administrador actualizado correctamente!");

        return ResponseEntity.ok(response);
    }

//    DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {

        Optional<Administrador> adminOptional = administradorService.findById(id);

        if (adminOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Administrador admin = adminOptional.get();

        admin.setIsEnabled(Boolean.FALSE);
        administradorService.guardarAdmin(admin);

        ListadoAdministradorDTO adminDTO = new ListadoAdministradorDTO(admin);

        APIResponseDTO response = new APIResponseDTO(adminDTO, "Aministrador eliminado");

        return ResponseEntity.ok(response);

    }
}
