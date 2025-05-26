package david.makao.controller;

import david.makao.model.UserEntity;
import david.makao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de mostrar la información del perfil del usuario autenticado.
 * Obtiene los datos del usuario desde la base de datos usando su email (nombre de usuario).
 *
 * <p>Accede a la ruta /perfil, la cual muestra la vista 'perfil.html' con los datos del usuario.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
public class PerfilController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Maneja las solicitudes GET para la vista del perfil del usuario autenticado.
     *
     * @param userDetails Datos del usuario autenticado proporcionados por Spring Security
     * @param model       Modelo para pasar los atributos a la vista
     * @return            Nombre de la plantilla Thymeleaf 'perfil.html'
     */
    @GetMapping("/perfil")
    public String verPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("usuario", user);
        return "perfil";
    }
}
