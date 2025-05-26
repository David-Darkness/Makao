package david.makao.service.impl;

import david.makao.model.*;
import david.makao.repository.*;
import david.makao.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Implementación de la interfaz {@link ReservationService}, que gestiona la lógica de negocio
 * relacionada con las reservas de paquetes turísticos.
 *
 * <p>Esta clase se encarga de crear, consultar, guardar y eliminar reservas,
 * y se apoya en múltiples repositorios para acceder a los datos persistentes.</p>
 *
 * <p>Está anotada con {@code @Service} para indicar que es un componente de servicio gestionado por Spring.</p>
 *
 * @author David
 * @version 1.0
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TourPackageRepository tourPackageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Crea y guarda una nueva reserva en base a los datos proporcionados desde una pasarela o formulario.
     *
     * @param datos Mapa con los campos necesarios: packageId, userId, hotelId, restaurantId,
     *              numberOfPeople, startDate y totalPrice.
     */
    @Override
    public void crearReserva(Map<String, String> datos) {
        Long packageId = Long.parseLong(datos.get("packageId"));
        Long userId = Long.parseLong(datos.get("userId"));
        Long hotelId = Long.parseLong(datos.get("hotelId"));
        Long restaurantId = Long.parseLong(datos.get("restaurantId"));
        int numberOfPeople = Integer.parseInt(datos.get("numberOfPeople"));
        LocalDate startDate = LocalDate.parse(datos.get("startDate"));
        BigDecimal totalPrice = new BigDecimal(datos.get("totalPrice"));

        TourPackageEntity paquete = tourPackageRepository.findById(packageId).orElse(null);
        UserEntity user = userRepository.findById(userId).orElse(null);
        HotelEntity hotel = hotelRepository.findById(hotelId).orElse(null);
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if (paquete != null && user != null && hotel != null && restaurant != null) {
            ReservationEntity reserva = new ReservationEntity();
            reserva.setUser(user);
            reserva.setTourPackage(paquete);
            reserva.setHotel(hotel);
            reserva.setRestaurant(restaurant);
            reserva.setStartDate(startDate);
            reserva.setEndDate(startDate.plusDays(paquete.getDurationDays()));
            reserva.setNumberOfPeople(numberOfPeople);
            reserva.setTotalPrice(totalPrice);
            reserva.setReservationDate(LocalDate.now());

            reservationRepository.save(reserva);
        }
    }

    /**
     * Obtiene una lista de reservas asociadas a un usuario específico.
     *
     * @param user Entidad {@link UserEntity} del usuario.
     * @return Lista de reservas asociadas al usuario.
     */
    @Override
    public List<ReservationEntity> findByUser(UserEntity user) {
        return reservationRepository.findByUser(user);
    }

    /**
     * Recupera todas las reservas existentes.
     *
     * @return Lista completa de reservas.
     */
    @Override
    public List<ReservationEntity> getAllReservations() {
        return (List<ReservationEntity>) reservationRepository.findAll();
    }

    /**
     * Busca una reserva por su identificador único.
     *
     * @param id ID de la reserva.
     * @return La reserva encontrada o {@code null} si no existe.
     */
    @Override
    public ReservationEntity buscarPorId(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    /**
     * Guarda o actualiza una reserva.
     *
     * @param reserva Entidad {@link ReservationEntity} a guardar.
     */
    @Override
    public void guardar(ReservationEntity reserva) {
        reservationRepository.save(reserva);
    }

    /**
     * Elimina una reserva por su identificador.
     *
     * @param id ID de la reserva a eliminar.
     */
    @Override
    public void eliminar(Long id) {
        reservationRepository.deleteById(id);
    }
}
