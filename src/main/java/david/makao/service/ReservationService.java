package david.makao.service;

import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * Interfaz para el servicio de gestión de reservas.
 * Define los métodos para crear, buscar, guardar y eliminar reservas.
 *
 * @author David
 * @version 1.0
 */
public interface ReservationService {

    /**
     * Crea una nueva reserva a partir de un mapa de datos.
     * Las claves esperadas en el mapa incluyen: "packageId", "userId", "hotelId",
     * "restaurantId", "numberOfPeople", "startDate", "totalPrice".
     *
     * @param datos mapa con los datos necesarios para crear la reserva
     */
    void crearReserva(Map<String, String> datos);

    /**
     * Obtiene una lista de reservas asociadas a un usuario específico.
     *
     * @param user entidad del usuario para filtrar reservas
     * @return lista de reservas del usuario
     */
    List<ReservationEntity> findByUser(UserEntity user);

    /**
     * Obtiene todas las reservas existentes.
     *
     * @return lista con todas las reservas
     */
    List<ReservationEntity> getAllReservations();

    /**
     * Busca una reserva por su ID.
     *
     * @param id ID de la reserva a buscar
     * @return la reserva encontrada, o null si no existe
     */
    ReservationEntity buscarPorId(Long id);

    /**
     * Guarda o actualiza una reserva en la base de datos.
     *
     * @param reserva entidad de reserva a guardar
     */
    void guardar(ReservationEntity reserva);

    /**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar
     */
    void eliminar(Long id);
}

