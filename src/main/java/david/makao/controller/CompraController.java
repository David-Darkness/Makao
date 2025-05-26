package david.makao.controller;

import david.makao.model.*;
import david.makao.repository.*;
import david.makao.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador encargado del flujo de compra de paquetes turísticos.
 *
 * <p>Permite mostrar el formulario de compra con selección de hotel y restaurante
 * asociados a la ciudad del paquete, y procesar la reserva tras la confirmación de pago.</p>
 *
 * <p>Requiere autenticación con rol {@code ADMIN}, {@code COLLABORATOR} o {@code CUSTOMER}.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR','CUSTOMER')")
@RequestMapping("/comprar")
public class CompraController {

    @Autowired
    private TourPackageRepository tourPackageRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Muestra el formulario de compra de un paquete turístico.
     *
     * <p>El formulario incluye la información del paquete, y una lista de hoteles
     * y restaurantes disponibles en la misma ciudad.</p>
     *
     * @param packageId ID del paquete turístico
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de compra, o redirección a página de error si no se encuentra el paquete
     */
    @GetMapping("/{packageId}")
    public String mostrarFormularioCompra(@PathVariable Long packageId, Model model) {
        Optional<TourPackageEntity> paqueteOpt = tourPackageRepository.findById(packageId);
        if (paqueteOpt.isEmpty()) {
            return "redirect:/error";
        }

        TourPackageEntity paquete = paqueteOpt.get();
        CityEntity ciudad = paquete.getCity();

        model.addAttribute("tourPackage", paquete);
        model.addAttribute("hotels", hotelRepository.findByCity_CityId(ciudad.getCityId()));
        model.addAttribute("restaurants", restaurantRepository.findByCity_CityId(ciudad.getCityId()));

        return "comprar-paquete";
    }

    /**
     * Procesa la confirmación de pago y registra la reserva en la base de datos.
     *
     * <p>Obtiene el usuario autenticado, recolecta los datos enviados por el formulario,
     * y delega la creación de la reserva al servicio correspondiente. Finalmente,
     * se redirige al usuario a una vista de resumen de la compra.</p>
     *
     * @param tourPackageId ID del paquete turístico seleccionado
     * @param hotelId ID del hotel seleccionado
     * @param restaurantId ID del restaurante seleccionado
     * @param startDate Fecha de inicio del viaje
     * @param numberOfPeople Cantidad de personas en la reserva
     * @param totalPrice Precio total calculado
     * @param model Modelo para pasar los datos a la vista de resumen
     * @return Nombre de la vista que muestra el resumen de la compra
     */
    @PostMapping("/confirmar-pago")
    public String confirmarPago(
            @RequestParam Long tourPackageId,
            @RequestParam Long hotelId,
            @RequestParam Long restaurantId,
            @RequestParam String startDate,
            @RequestParam int numberOfPeople,
            @RequestParam BigDecimal totalPrice,
            Model model
    ) {
        // Obtener usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByEmail(username).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        // Preparar datos para la reserva
        Map<String, String> datos = new HashMap<>();
        datos.put("packageId", String.valueOf(tourPackageId));
        datos.put("hotelId", String.valueOf(hotelId));
        datos.put("restaurantId", String.valueOf(restaurantId));
        datos.put("startDate", startDate);
        datos.put("numberOfPeople", String.valueOf(numberOfPeople));
        datos.put("totalPrice", totalPrice.toString());
        datos.put("userId", String.valueOf(user.getId()));

        // Guardar reserva
        reservationService.crearReserva(datos);

        // Cargar datos para mostrar resumen
        model.addAttribute("tourPackage", tourPackageRepository.findById(tourPackageId).orElse(null));
        model.addAttribute("hotel", hotelRepository.findById(hotelId).orElse(null));
        model.addAttribute("restaurant", restaurantRepository.findById(restaurantId).orElse(null));
        model.addAttribute("startDate", startDate);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("totalPrice", totalPrice);

        return "resumen-compra";
    }
}

