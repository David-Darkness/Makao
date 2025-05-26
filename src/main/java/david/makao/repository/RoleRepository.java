package david.makao.repository;

import david.makao.model.ERole;
import david.makao.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repositorio para la gestión de datos relacionados con {@link RoleEntity}.
 * Extiende {@link CrudRepository} para proporcionar operaciones CRUD básicas sobre roles.
 *
 * <p>Incluye métodos personalizados para buscar roles por enumeración {@link ERole}.</p>
 *
 * <p>Métodos personalizados:</p>
 * <ul>
 *     <li>{@code findByRole(ERole role)}: Busca un rol específico por su enumeración.</li>
 *     <li>{@code existsByRole(ERole role)}: Verifica si un rol existe por su enumeración.</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    /**
     * Busca un rol específico por su valor en {@link ERole}.
     *
     * @param role Enumeración del rol.
     * @return Un {@link Optional} que puede contener el {@link RoleEntity} encontrado.
     */
    Optional<RoleEntity> findByRole(ERole role);

    /**
     * Verifica si existe un rol específico por su valor en {@link ERole}.
     *
     * @param role Enumeración del rol.
     * @return {@code true} si existe el rol, {@code false} en caso contrario.
     */
    boolean existsByRole(ERole role);
}
