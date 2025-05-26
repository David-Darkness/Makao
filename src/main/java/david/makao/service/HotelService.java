package david.makao.service;

import david.makao.model.HotelEntity;

import java.util.List;

/**
 * Interfaz para el servicio de gestión de hoteles.
 * Define los métodos para obtener hoteles de la base de datos.
 *
 * @author David
 * @version 1.0
 */
public interface HotelService {

    /**
     * Obtiene la lista de todos los hoteles disponibles.
     *
     * @return lista con todos los hoteles
     */
    List<HotelEntity> getAllHotels();

    /**
     * Obtiene la lista de hoteles que pertenecen a una ciudad específica.
     *
     * @param cityId el ID de la ciudad para filtrar hoteles
     * @return lista de hoteles asociados a la ciudad dada
     */
    List<HotelEntity> getHotelsByCity(Long cityId);
}
