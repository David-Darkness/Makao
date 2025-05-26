package david.makao.service.impl;

import david.makao.model.RestaurantEntity;
import david.makao.repository.RestaurantRepository;
import david.makao.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link RestaurantService} que gestiona la lógica
 * relacionada con los restaurantes.
 *
 * <p>Proporciona métodos para obtener, guardar y eliminar restaurantes,
 * así como para consultar restaurantes por ciudad o por ID.</p>
 *
 * <p>Clase anotada con {@code @Service} para ser gestionada por Spring y con
 * {@code @RequiredArgsConstructor} para inyección automática del repositorio.</p>
 *
 * @author David
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private final RestaurantRepository restaurantRepository;

    /**
     * Obtiene la lista completa de restaurantes.
     *
     * @return lista de todas las entidades {@link RestaurantEntity}.
     */
    @Override
    public List<RestaurantEntity> getAllRestaurants() {
        return (List<RestaurantEntity>) restaurantRepository.findAll();
    }

    /**
     * Obtiene los restaurantes que pertenecen a una ciudad específica.
     *
     * @param cityId ID de la ciudad.
     * @return lista de restaurantes asociados a la ciudad.
     */
    @Override
    public List<RestaurantEntity> getRestaurantsByCity(Long cityId) {
        return restaurantRepository.findByCity_CityId(cityId);
    }

    /**
     * Obtiene un restaurante por su identificador.
     *
     * @param id ID del restaurante.
     * @return la entidad {@link RestaurantEntity} correspondiente o {@code null} si no existe.
     */
    @Override
    public RestaurantEntity getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    /**
     * Guarda o actualiza un restaurante.
     *
     * @param restaurante entidad {@link RestaurantEntity} a guardar.
     */
    @Override
    public void saveRestaurant(RestaurantEntity restaurante) {
        restaurantRepository.save(restaurante);
    }

    /**
     * Elimina un restaurante por su ID.
     *
     * @param id ID del restaurante a eliminar.
     */
    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}


