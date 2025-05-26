package david.makao.service.impl;

import david.makao.model.HotelEntity;
import david.makao.repository.HotelRepository;
import david.makao.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link HotelService} para la gestión de entidades {@link HotelEntity}.
 * Proporciona lógica de negocio relacionada con la obtención de hoteles.
 *
 * <p>Esta clase utiliza {@link HotelRepository} para acceder a los datos persistentes.</p>
 *
 * <p>Anotada con {@code @Service} para indicar que es un componente de servicio de Spring,
 * y con {@code @RequiredArgsConstructor} de Lombok para generar el constructor requerido automáticamente.</p>
 *
 * @author David
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    /**
     * Obtiene la lista completa de hoteles disponibles en el sistema.
     *
     * @return Lista de {@link HotelEntity}.
     */
    @Override
    public List<HotelEntity> getAllHotels() {
        return (List<HotelEntity>) hotelRepository.findAll();
    }

    /**
     * Obtiene una lista de hoteles filtrados por ciudad.
     *
     * @param cityId Identificador único de la ciudad.
     * @return Lista de {@link HotelEntity} asociados a la ciudad especificada.
     */
    @Override
    public List<HotelEntity> getHotelsByCity(Long cityId) {
        return hotelRepository.findByCity_CityId(cityId);
    }
}

