package david.makao.repository;

import david.makao.model.TourPackageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de entidades {@link TourPackageEntity}.
 * Extiende {@link CrudRepository} para proporcionar operaciones CRUD básicas sobre los paquetes turísticos.
 *
 * <p>Incluye métodos personalizados para la búsqueda por ciudad asociada.</p>
 *
 * <p>Métodos personalizados:</p>
 * <ul>
 *     <li>{@code findByCity_CityId(Long cityId)}: Lista los paquetes turísticos de una ciudad específica.</li>
 *     <li>{@code findAll()}: Recupera todos los paquetes turísticos en la base de datos.</li>
 * </ul>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface TourPackageRepository extends CrudRepository<TourPackageEntity, Long> {

    /**
     * Obtiene todos los paquetes turísticos asociados a una ciudad específica.
     *
     * @param cityId ID de la ciudad.
     * @return Lista de {@link TourPackageEntity} asociados a la ciudad.
     */
    List<TourPackageEntity> findByCity_CityId(Long cityId);

    /**
     * Obtiene todos los paquetes turísticos registrados.
     *
     * @return Lista de todos los {@link TourPackageEntity}.
     */
    List<TourPackageEntity> findAll();
}

