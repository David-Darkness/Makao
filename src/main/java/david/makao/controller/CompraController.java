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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    // Mostrar formulario (ya lo tienes, pero para completar)
    @GetMapping("/{packageId}")
    public String mostrarFormularioCompra(@PathVariable Long packageId, Model model) {
        Optional<TourPackageEntity> paqueteOpt = tourPackageRepository.findById(packageId);
        if (paqueteOpt.isEmpty()) {
            return "redirect:/error"; // O página de error
        }

        TourPackageEntity paquete = paqueteOpt.get();
        CityEntity ciudad = paquete.getCity();

        model.addAttribute("tourPackage", paquete);
        model.addAttribute("hotels", hotelRepository.findByCity_CityId(ciudad.getCityId()));
        model.addAttribute("restaurants", restaurantRepository.findByCity_CityId(ciudad.getCityId()));

        return "comprar-paquete"; // El nombre de tu html
    }



    // Procesar la compra (formulario POST)
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

        UserEntity user = userRepository.findByEmail(username).orElse(null);  // <- corrección
 // o findByUsername si usas otro campo
        if (user == null) {
            return "redirect:/login";  // Si no está autenticado
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

        // Recuperar la reserva para mostrar resumen (opcional: crear método para obtener última reserva del usuario)
        // Por simplicidad, podrías pasar los datos directamente a la vista:
        model.addAttribute("tourPackage", tourPackageRepository.findById(tourPackageId).orElse(null));
        model.addAttribute("hotel", hotelRepository.findById(hotelId).orElse(null));
        model.addAttribute("restaurant", restaurantRepository.findById(restaurantId).orElse(null));
        model.addAttribute("startDate", startDate);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("totalPrice", totalPrice);

        return "resumen-compra";  // Vista que muestra resumen de la compra
    }
}

