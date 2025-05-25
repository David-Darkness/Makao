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

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TourPackageRepository tourPackageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;  // FALTABA

    @Autowired
    private RestaurantRepository restaurantRepository;  // FALTABA


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
        HotelEntity hotel = hotelRepository.findById(hotelId).orElse(null); // CORREGIDO
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if (paquete != null && user != null && hotel != null && restaurant != null) {
            ReservationEntity reserva = new ReservationEntity();
            reserva.setUser(user);
            reserva.setTourPackage(paquete);
            reserva.setHotel(hotel);
            reserva.setRestaurant(restaurant);
            reserva.setStartDate(startDate);
            reserva.setEndDate(startDate.plusDays(paquete.getDurationDays())); // si tienes duración en el paquete
            reserva.setNumberOfPeople(numberOfPeople);
            reserva.setTotalPrice(totalPrice);
            reserva.setReservationDate(LocalDate.now());

            reservationRepository.save(reserva);
        }
    }

    @Override
    public List<ReservationEntity> findByUser(UserEntity user) {
        return reservationRepository.findByUser(user);
    }

    @Override
    public List<ReservationEntity> getAllReservations() {
        return (List<ReservationEntity>) reservationRepository.findAll();
    }

    @Override
    public ReservationEntity buscarPorId(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(ReservationEntity reserva) {
        reservationRepository.save(reserva);
    }

    @Override
    public void eliminar(Long id) {
        reservationRepository.deleteById(id);
    }
}
