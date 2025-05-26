package david.makao.repository;

import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de datos relacionados con {@link ReservationEntity}.
 * Extiende {@link CrudRepository} para proporcionar operaciones CRUD básicas.
 *
 * <p>Incluye métodos personalizados para obtener reservas por usuario.</p>
 *
 * <p>Métodos personalizados:</p>
 * <ul>
 *     <li>{@code findByUser(UserEntity user)}: Devuelve todas las reservas asociadas a un usuario específico.</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

    /**
     * Busca todas las reservas asociadas a un usuario específico.
     *
     * @param user Usuario cuyas reservas se desean obtener.
     * @return Lista de reservas asociadas al usuario.
     */
    List<ReservationEntity> findByUser(UserEntity user);
}
