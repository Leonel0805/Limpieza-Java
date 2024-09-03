package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthLoginDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthResponseDTO;
import Proyecto_Limpieza.app.limpieza.services.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid AuthRegisterDTO authRegisterDTO) {

    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody @Valid AuthLoginDTO authLoginDTO) {

        AuthResponseDTO response  = this.userDetailService.login(authLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
