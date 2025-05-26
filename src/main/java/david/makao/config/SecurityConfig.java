package david.makao.config;

import david.makao.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración principal de seguridad para la aplicación.
 *
 * <p>Esta clase configura:
 * <ul>
 *   <li>Autenticación basada en usuarios de la base de datos</li>
 *   <li>Protección de endpoints</li>
 *   <li>Configuración de formulario de login</li>
 *   <li>Manejo de sesiones</li>
 *   <li>Codificación de contraseñas</li>
 * </ul>
 *
 * @author David Makao
 * @version 1.0
 * @see EnableWebSecurity
 * @see EnableGlobalMethodSecurity
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Configura el codificador de contraseñas usando BCrypt.
     *
     * @return Instancia de {@link BCryptPasswordEncoder} para codificar contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el AuthenticationManager para la autenticación de usuarios.
     *
     * @param http Configuración de seguridad HTTP
     * @param passwordEncoder Codificador de contraseñas
     * @param userDetailsService Servicio para cargar detalles de usuario
     * @return AuthenticationManager configurado
     * @throws Exception Si ocurre un error durante la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     *
     * <p>Esta configuración incluye:
     * <ul>
     *   <li>Deshabilitación de CSRF</li>
     *   <li>Autorización de endpoints públicos</li>
     *   <li>Configuración del formulario de login</li>
     *   <li>Manejo de excepciones de acceso</li>
     *   <li>Configuración de logout</li>
     *   <li>Política de manejo de sesiones</li>
     * </ul>
     *
     * @param http Configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     * @throws Exception Si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/signup",
                                "/createUser",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/process_login",
                                "/register",
                                "/home",
                                "/navbar",
                                "/paquetes"
                        ).permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .build();
    }
}
