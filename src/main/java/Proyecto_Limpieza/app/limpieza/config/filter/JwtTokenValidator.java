package Proyecto_Limpieza.app.limpieza.config.filter;

import Proyecto_Limpieza.app.limpieza.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

// Filtro
public class JwtTokenValidator extends OncePerRequestFilter { //esto hace que cada vez que se hace una request, se ejecute este filtro

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {


//        cuando se ejecuta el filtro recibimos el token
        String jwttoken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwttoken != null) {

            String JwtToken = jwttoken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validarToken(JwtToken);

            String username = jwtUtils.extraerUsername(decodedJWT);
            String stringAuthorities = jwtUtils.extraerClaim(decodedJWT, "Authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context = SecurityContextHolder.getContext();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

//            le enviamos la authenticacion al contexto de spring security
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

//        continuamos con el siguiente filtro, en caso de token ser null por lo tanto es rechazado
        filterChain.doFilter(request, response);



    }
}
