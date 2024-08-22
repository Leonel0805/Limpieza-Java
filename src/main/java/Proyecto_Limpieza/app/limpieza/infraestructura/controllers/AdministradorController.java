package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.DatosAdministrador;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.DatosListadoAdministrador;
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
    public ResponseEntity<List<DatosListadoAdministrador>> findAll() {
//        Convertimos a lista de DTO para mostrar los datos que queremos
        List<DatosListadoAdministrador> list_administradores = administradorService.findAll().stream()
                .map(administrador -> new DatosListadoAdministrador(administrador))
                .collect(Collectors.toList());


        return ResponseEntity.ok(list_administradores);
    }

//    Get admin byid
    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoAdministrador> findById(@PathVariable Long id) {

        Optional<Administrador> adminOptional = administradorService.findById(id);

        if (adminOptional.isPresent()) {
            Administrador admin = adminOptional.get();
            DatosListadoAdministrador adminReturn = new DatosListadoAdministrador(admin);
            return ResponseEntity.ok(adminReturn);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    POST admin
    @PostMapping
    public ResponseEntity guardarAdmin(@RequestBody @Valid DatosAdministrador datosadministrador) throws URISyntaxException {

//        Convertimos lo enviado por el client en un objeto
        Administrador admin = new Administrador(datosadministrador);

//        agregamos rol
//        admin.getRoles().add()

//        Lo guardamos en la database
        administradorService.guardarAdmin(admin);

//      Modificamos la forma de ver los datos, enviar DTO
        DatosListadoAdministrador adminDTO = new DatosListadoAdministrador(admin);

        APIResponseDTO response = new APIResponseDTO(adminDTO, "Administrador creado correctamente!");

        return ResponseEntity.created(new URI("/api/administradores"))
                .body(response);
    }

//    PUT
    @PutMapping("/{id}")
    public ResponseEntity actualizarAdmin(@PathVariable Long id, @RequestBody @Valid DatosAdministrador datosAdministrador) {

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
        DatosListadoAdministrador adminDTO = new DatosListadoAdministrador(admin);

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

        DatosListadoAdministrador adminDTO = new DatosListadoAdministrador(admin);

        APIResponseDTO response = new APIResponseDTO(adminDTO, "Aministrador eliminado");

        return ResponseEntity.ok(response);

    }
}
