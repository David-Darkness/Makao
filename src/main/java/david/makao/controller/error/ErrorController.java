package david.makao.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para manejar errores de acceso denegado en la aplicación.
 *
 * <p>Este controlador proporciona una vista personalizada cuando un usuario intenta
 * acceder a una página o recurso sin los permisos adecuados, mostrando una interfaz
 * clara que indica que el acceso está restringido.</p>
 *
 * <p>Normalmente es invocado por Spring Security al detectar un intento de acceso
 * no autorizado.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
public class ErrorController {

    /**
     * Maneja las solicitudes a la ruta "/access-denied", que se activa cuando un usuario
     * intenta acceder a un recurso protegido sin la autorización necesaria.
     *
     * @return Nombre de la vista Thymeleaf personalizada que indica acceso denegado
     *
     * @implNote La vista correspondiente debe estar ubicada en `src/main/resources/templates/access-denied.html`
     */
    @GetMapping("/access-denied")
    public String accesoDenegado() {
        return "access-denied";
    }
}
