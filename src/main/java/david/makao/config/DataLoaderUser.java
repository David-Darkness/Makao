/*
package david.makao.config;

import david.makao.model.ERole;
import david.makao.model.RoleEntity;
import david.makao.model.UserEntity;
import david.makao.repository.RoleRepository;
import david.makao.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class DataLoaderUser {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Crear roles si no existen
            for (ERole role : ERole.values()) {
                if (!roleRepository.existsByRole(role)) {
                    RoleEntity newRole = RoleEntity.builder()
                            .role(role)
                            .build();
                    roleRepository.save(newRole);
                }
            }

            // Crear usuario admin si no existe
            String adminEmail = "polancoortizjuandavid2@gmail.com";
            if (!userRepository.existsByEmail(adminEmail)) {
                RoleEntity adminRole = roleRepository.findByRole(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

                UserEntity adminUser = UserEntity.builder()
                        .email(adminEmail)
                        .name("Juan David")
                        .lastName("Polanco")
                        .username("David-Darkness")
                        .password(passwordEncoder.encode("Admin123"))
                        .phone("3125587825")
                        .identificationNumber("1078776688")
                        .build();

                // Asigna el rol directamente sin usar métodos helpers
                adminUser.getRoles().add(adminRole);
                userRepository.save(adminUser);
            }
        };
    }
}
*/