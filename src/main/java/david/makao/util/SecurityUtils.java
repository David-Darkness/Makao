package david.makao.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utilidad para obtener información del usuario autenticado en el contexto de seguridad.
 *
 * @author David
 * @version 1.0
 */
public class SecurityUtils {

    /**
     * Obtiene el nombre de usuario del usuario actualmente autenticado en el contexto de seguridad.
     *
     * @return el nombre de usuario si está autenticado, "anonymousUser" si es un usuario anónimo,
     *         o "Usuario" si no hay autenticación disponible.
     */
    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString(); // Por ejemplo: "anonymousUser"
            }
        }
        return "Usuario";
    }
}
