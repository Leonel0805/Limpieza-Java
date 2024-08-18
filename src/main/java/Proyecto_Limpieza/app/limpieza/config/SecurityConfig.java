package Proyecto_Limpieza.app.limpieza.config;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity //habilitar seguridad web
@EnableMethodSecurity //habilitar anotaciones de seguridad para poner en el controller y no configurar en mi filterChain
public class SecurityConfig {


//    Filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        hay que configurar los filtros
        return httpSecurity
                .csrf(csrf -> csrf.disable()) //desactivamos porque no lo necesitamos
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //sin estado, seria para cuando los navegadores te piden cada cierto tiempo volver a iniciar sesion, sesiones que se guardan en memoria
                //aca si config los endpoints
//                .authorizeHttpRequests(http -> {
////                    Configurar endpoints publicos
//                    http.requestMatchers(HttpMethod.GET, "/api/articulos").permitAll();
//
////                    Configurar endpoints privados
//                    http.requestMatchers(HttpMethod.POST, "/api/articulos").hasAuthority("CREATE");
//
////                    Configurar endpoints denegados
//                    http.anyRequest().denyAll();
//                })
                .build();
    }

//    Authenticaton Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //    Provider database
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

//        el provider necesita estos 2 componentes
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {

//        simulamos la base de datos crenando un UserDetails
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("leonel")
                .password("leonel")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());

        userDetailsList.add(User.withUsername("juan")
                .password("juan")
                .roles("USER")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
