package david.makao.repository;

import david.makao.model.RestaurantEntity;
import david.makao.model.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de datos relacionados con {@link RestaurantEntity}.
 * Extiende {@link CrudRepository} para proporcionar operaciones CRUD básicas.
 *
 * <p>Incluye métodos personalizados para obtener restaurantes por ciudad.</p>
 *
 * <p>Métodos personalizados:</p>
 * <ul>
 *     <li>{@code findByCity_CityId(Long cityId)}: Devuelve todos los restaurantes que pertenecen a una ciudad específica.</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {

    /**
     * Busca todos los restaurantes asociados a una ciudad específica.
     *
     * @param cityId ID de la ciudad.
     * @return Lista de restaurantes pertenecientes a la ciudad.
     */
    List<RestaurantEntity> findByCity_CityId(Long cityId);
}
