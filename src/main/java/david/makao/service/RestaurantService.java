package david.makao.service;

import david.makao.model.RestaurantEntity;
import java.util.List;

/**
 * Interfaz para el servicio de gestión de restaurantes.
 * Define los métodos para obtener, guardar y eliminar restaurantes.
 *
 * @author David
 * @version 1.0
 */
public interface RestaurantService {

    /**
     * Obtiene la lista de todos los restaurantes disponibles.
     *
     * @return lista con todos los restaurantes
     */
    List<RestaurantEntity> getAllRestaurants();

    /**
     * Obtiene la lista de restaurantes que pertenecen a una ciudad específica.
     *
     * @param cityId el ID de la ciudad para filtrar restaurantes
     * @return lista de restaurantes asociados a la ciudad dada
     */
    List<RestaurantEntity> getRestaurantsByCity(Long cityId);

    /**
     * Obtiene un restaurante por su ID.
     *
     * @param id ID del restaurante a buscar
     * @return entidad del restaurante encontrado, o null si no existe
     */
    RestaurantEntity getRestaurantById(Long id);

    /**
     * Guarda o actualiza un restaurante en la base de datos.
     *
     * @param restaurante entidad del restaurante a guardar
     */
    void saveRestaurant(RestaurantEntity restaurante);

    /**
     * Elimina un restaurante por su ID.
     *
     * @param id ID del restaurante a eliminar
     */
    void deleteRestaurant(Long id);
}

