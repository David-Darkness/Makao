// src/main/java/david/makao/controller/CustomErrorController.java
package david.makao.controller.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador personalizado para manejo de errores en la aplicación.
 * Implementa la interfaz ErrorController de Spring Boot para proporcionar
 * manejo centralizado de errores HTTP y mostrar páginas de error personalizadas.
 *
 * <p>Este controlador captura todos los errores y muestra una vista de error
 * unificada con información relevante sobre el problema ocurrido.</p>
 *
 * @author David
 * @version 1.0
 * @see org.springframework.boot.web.servlet.error.ErrorController
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Maneja las solicitudes de error y prepara el modelo con información detallada.
     *
     * @param request Objeto HttpServletRequest que contiene los atributos de error
     * @param model Modelo para pasar datos a la vista de error
     * @return Nombre de la vista de error personalizada
     *
     * @implNote Este método captura los siguientes atributos de error:
     * <ul>
     *   <li>Código de estado HTTP (ERROR_STATUS_CODE)</li>
     *   <li>Mensaje de error (ERROR_MESSAGE)</li>
     *   <li>Excepción asociada (ERROR_EXCEPTION)</li>
     * </ul>
     * Si alguno de estos atributos no está disponible, se proporcionan valores por defecto.
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        model.addAttribute("status", status);
        model.addAttribute("error", error != null ? error : "Ha ocurrido un error");
        model.addAttribute("message", exception != null ? exception.toString() : "Algo salió mal. Intenta más tarde.");
        return "error";
    }
}
