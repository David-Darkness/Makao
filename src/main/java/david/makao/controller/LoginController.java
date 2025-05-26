package david.makao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de manejar las rutas relacionadas con el inicio de sesión
 * y la visualización del navbar.
 *
 * <p>Este controlador sirve principalmente vistas estáticas como el formulario de login
 * y la barra de navegación.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
public class LoginController {

    /**
     * Muestra la página de inicio de sesión.
     *
     * @return Nombre de la vista "login" (login.html en /templates)
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Muestra la plantilla del navbar.
     *
     * @return Nombre de la vista "navbar" (navbar.html en /templates)
     */
    @GetMapping("/navbar")
    public String navbar() {
        return "navbar";
    }
}

