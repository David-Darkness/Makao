package david.makao.repository;

import david.makao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestión de entidades {@link UserEntity}.
 * Extiende {@link JpaRepository} para proporcionar operaciones CRUD avanzadas y soporte para paginación y ordenamiento.
 *
 * <p>Incluye métodos personalizados para la búsqueda de usuarios por correo electrónico y verificación de existencia por email o username.</p>
 *
 * <p>Métodos personalizados:</p>
 * <ul>
 *     <li>{@code findByEmail(String email)}: Busca un usuario por su dirección de correo electrónico.</li>
 *     <li>{@code existsByEmail(String email)}: Verifica si existe un usuario con un correo dado.</li>
 *     <li>{@code existsByUsername(String username)}: Verifica si existe un usuario con un nombre de usuario específico.</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email Correo electrónico del usuario.
     * @return Un {@link Optional} conteniendo el {@link UserEntity} si existe.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario con el correo electrónico proporcionado.
     *
     * @param email Correo electrónico a verificar.
     * @return {@code true} si existe un usuario con ese email, de lo contrario {@code false}.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si ya existe un usuario con el nombre de usuario proporcionado.
     *
     * @param username Nombre de usuario a verificar.
     * @return {@code true} si existe un usuario con ese username, de lo contrario {@code false}.
     */
    boolean existsByUsername(String username);
}
