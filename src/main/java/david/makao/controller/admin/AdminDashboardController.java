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

/**
 * Controlador para el panel de administración que gestiona reservas, hoteles y restaurantes.
 *
 * <p>Este controlador proporciona funcionalidades para:
 * <ul>
 *   <li>Visualizar todas las reservas</li>
 *   <li>Editar reservas existentes</li>
 *   <li>Guardar nuevas reservas o cambios</li>
 *   <li>Eliminar reservas</li>
 * </ul>
 *
 * <p>Acceso restringido a roles ADMIN y COLLABORATOR mediante Spring Security.
 *
 * @author David Makao
 * @version 1.0
 * @see ReservationService
 * @see HotelService
 * @see RestaurantService
 */
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

    /**
     * Muestra el panel de administración con todas las reservas.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista del panel de administración
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ReservationEntity> reservas = reservationService.getAllReservations();
        model.addAttribute("reservas", reservas);
        return "admin/dashboard";
    }

    /**
     * Lista todas las reservas (redirige al dashboard).
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista del panel de administración
     */
    @GetMapping("")
    public String listarReservas(Model model) {
        List<ReservationEntity> reservas = reservationService.getAllReservations();
        model.addAttribute("reservas", reservas);
        return "admin/dashboard";
    }

    /**
     * Muestra el formulario para editar una reserva existente.
     *
     * @param id ID de la reserva a editar
     * @param model Modelo para pasar datos a la vista
     * @return Vista del formulario de reserva
     */
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

    /**
     * Procesa el guardado de una reserva (nueva o editada).
     *
     * @param reserva Entidad ReservationEntity con los datos del formulario
     * @return Redirección al panel de administración
     */
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute ReservationEntity reserva) {
        reservationService.guardar(reserva);
        return "redirect:/admin";
    }

    /**
     * Elimina una reserva existente.
     *
     * @param id ID de la reserva a eliminar
     * @return Redirección al panel de administración
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservationService.eliminar(id);
        return "redirect:/admin";
    }
}