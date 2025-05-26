package david.makao.controller;

import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;
import david.makao.service.ReservationService;
import david.makao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controlador para gestionar las reservas del usuario autenticado.
 * Muestra todas las reservas asociadas al usuario que ha iniciado sesión.
 *
 * <p>Accede a la ruta /reservas, mostrando la vista 'mis-reservas.html' con la lista de reservas.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    /**
     * Muestra la lista de reservas del usuario autenticado.
     *
     * @param userDetails Datos del usuario autenticado proporcionados por Spring Security
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la plantilla Thymeleaf 'mis-reservas.html' con las reservas
     */
    @GetMapping("/reservas")
    public String verReservasUsuario(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";  // Redirigir si no está autenticado
        }

        UserEntity usuario = userService.findByEmail(userDetails.getUsername());
        if (usuario == null) {
            return "redirect:/login";  // Redirigir si no se encontró el usuario
        }

        List<ReservationEntity> reservas = reservationService.findByUser(usuario);
        model.addAttribute("reservas", reservas);

        return "mis-reservas";
    }
}
