package Proyecto_Limpieza.app.limpieza.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    //    importamos lo de nuestro properties para la autenticidad de nuestro jwt
    @Value("${SPRING_JWT_PRIVATE}")
    private String privateKey;

    @Value("${SPRING_JWT_USER}")
    private String userGenerator;

    // Crear Token
    public String crearToken(Authentication authentication) {

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey); //le pasamos nuestra private key para autenticidad de backend

        String username = authentication.getName(); //o .getname()

//        guardamos los authorities en string separados por,
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toString();

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities) //agregamos para ver authorities en el jwt
                .withIssuedAt(new Date()) //agregamos fecha
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // expira en 30 minutos
                .withJWTId(UUID.randomUUID().toString()) // asignar id
                .withNotBefore(new Date(System.currentTimeMillis())) //valido a partir de ahora
                .sign(algorithm);

        return jwtToken;

    }

    //    Validar Token
    public DecodedJWT validarToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalido");
        }
    }

    //    metodos aparte
    public String extraerUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    public Claim extraerClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> extraerAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

}
