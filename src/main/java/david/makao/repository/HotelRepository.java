package david.makao.repository;

import david.makao.model.HotelEntity;
import david.makao.model.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder y gestionar datos relacionados con la entidad {@link HotelEntity}.
 * Extiende {@link CrudRepository} para operaciones CRUD básicas.
 *
 * <p>Incluye métodos personalizados para filtrar hoteles por ciudad y ordenarlos por estrellas o nombre.</p>
 *
 * <p>Los métodos personalizados incluyen:</p>
 * <ul>
 *     <li>{@code findByCity_CityId(Long id)}: Busca hoteles pertenecientes a una ciudad específica.</li>
 *     <li>{@code findAllByOrderByStarsDesc()}: Lista todos los hoteles ordenados por número de estrellas (descendente).</li>
 *     <li>{@code findAllByOrderByNameAsc()}: Lista todos los hoteles ordenados alfabéticamente por nombre (ascendente).</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Long> {

    /**
     * Obtiene todos los hoteles de una ciudad específica.
     *
     * @param id ID de la ciudad.
     * @return lista de hoteles pertenecientes a la ciudad.
     */
    List<HotelEntity> findByCity_CityId(Long id);

    /**
     * Obtiene todos los hoteles ordenados por número de estrellas en orden descendente.
     *
     * @return lista de hoteles ordenados por estrellas.
     */
    List<HotelEntity> findAllByOrderByStarsDesc();

    /**
     * Obtiene todos los hoteles ordenados alfabéticamente por nombre.
     *
     * @return lista de hoteles ordenados por nombre.
     */
    List<HotelEntity> findAllByOrderByNameAsc();
}

