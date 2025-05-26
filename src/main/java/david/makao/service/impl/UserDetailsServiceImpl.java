package david.makao.service.impl;

import david.makao.model.UserEntity;
import david.makao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Implementación del servicio {@link UserDetailsService} utilizado por Spring Security
 * para la autenticación y autorización de usuarios mediante su email.
 *
 * <p>Este servicio carga los detalles del usuario a partir del email, y convierte
 * los roles del usuario en autoridades (authorities) necesarias para el manejo
 * de permisos en Spring Security.</p>
 *
 * @author David
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga un usuario por su email, que será usado como nombre de usuario en Spring Security.
     *
     * @param email el email del usuario que se desea autenticar
     * @return un objeto {@link UserDetails} con la información de usuario y sus roles convertidos en autoridades
     * @throws UsernameNotFoundException si no existe un usuario con el email proporcionado
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));

        // Convertir roles a autoridades con el prefijo "ROLE_"
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toSet());

        // Construir y devolver el objeto User de Spring Security
        return new User(
                userEntity.getEmail(),       // email se usa como username
                userEntity.getPassword(),
                true,                        // enabled
                true,                        // accountNonExpired
                true,                        // credentialsNonExpired
                true,                        // accountNonLocked
                authorities                  // roles/authorities
        );
    }
}


