package david.makao.controller.admin;

import david.makao.model.HotelEntity;
import david.makao.model.ReservationEntity;
import david.makao.model.RestaurantEntity;
import david.makao.service.HotelService;
import david.makao.service.ReservationService;
import david.makao.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ReservationEntity> reservas = reservationService.getAllReservations();
        model.addAttribute("reservas", reservas);
        return "admin/dashboard";
    }

    @GetMapping("")
    public String listarReservas(Model model) {
        List<ReservationEntity> reservas = reservationService.getAllReservations();
        model.addAttribute("reservas", reservas);

        return "admin/dashboard";
    }

    @GetMapping("/editar/{id}")
    public String editarReserva(@PathVariable Long id, Model model) {
        ReservationEntity reserva = reservationService.buscarPorId(id);

        // Evitar NullPointerException en el form
        if (reserva.getHotel() == null) {
            reserva.setHotel(new HotelEntity());
        }
        if (reserva.getRestaurant() == null) {
            reserva.setRestaurant(new RestaurantEntity());
        }

        model.addAttribute("reserva", reserva);
        model.addAttribute("hoteles", hotelService.getAllHotels());
        model.addAttribute("restaurantes", restaurantService.getAllRestaurants());
        return "admin/reserva-form";
    }


    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute ReservationEntity reserva) {
        reservationService.guardar(reserva);
        return "redirect:/admin";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservationService.eliminar(id);
        return "redirect:/admin"; // <--- corregido
    }


}
