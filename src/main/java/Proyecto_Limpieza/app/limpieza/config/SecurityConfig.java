package Proyecto_Limpieza.app.limpieza.config;

import Proyecto_Limpieza.app.limpieza.config.filter.JwtTokenValidator;
import Proyecto_Limpieza.app.limpieza.services.UserDetailServiceImpl;

import Proyecto_Limpieza.app.limpieza.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity //habilitar seguridad web
public class SecurityConfig {


    //    agregamos el JwtTokenValidator
    @Autowired
    private JwtUtils jwtUtils;

//    Filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        hay que configurar los filtros
        return httpSecurity
                .csrf(csrf -> csrf.disable()) //desactivamos porque no lo necesitamos
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //sin estado, seria para cuando los navegadores te piden cada cierto tiempo volver a iniciar sesion, sesiones que se guardan en memoria
                //aca si config los endpoints

                .authorizeHttpRequests(http -> {
//                    Configurar endpoints publicos
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/articulos/**").permitAll();

                    http.requestMatchers(HttpMethod.GET, "/api/pedidos/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/roles/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/roles/**").permitAll();



//                   Configurar endpoints privados
//                    ARTICULOS
                    http.requestMatchers(HttpMethod.POST, "/api/articulos").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/articulos").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/articulos").hasRole("ADMIN");

//                    PEDIDOS
                    http.requestMatchers(HttpMethod.POST, "/api/pedidos").hasAnyRole("ADMIN", "ENCARGADO");
                    http.requestMatchers(HttpMethod.PUT, "/api/pedidos").hasAnyRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/pedidos").hasAnyRole("ADMIN");

//                    ADMINISTRADOR
                    http.requestMatchers(HttpMethod.GET, "/api/administradores/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/administradores").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/administradores/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/administradores/{id}").hasRole("ADMIN");


//                    Configurar endpoints denegados
                    http.anyRequest().authenticated();
                })

                //agregamos el filtro de validar Token
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)

                .build();
    }

//    Authenticaton Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //    Provider database
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

//        el provider necesita estos 2 componentes

        provider.setUserDetailsService(userDetailService);
//        provider.setUserDetailsService(null); //creamos user details service personalizado
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        este es para pruebas nada mas
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
