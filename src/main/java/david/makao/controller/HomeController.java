package david.makao.controller;

import org.springframework.ui.Model;
import david.makao.model.TourPackageEntity;
import david.makao.service.TourPackageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Controlador encargado de gestionar las vistas públicas del sitio web, como el home,
 * los destinos, la página de contacto y los detalles de los paquetes turísticos.
 *
 * <p>Este controlador no requiere autenticación, ya que está orientado al acceso general
 * del usuario visitante.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
public class HomeController {

    private final TourPackageService tourPackageService;


    /**
     * Constructor para inyectar el servicio de paquetes turísticos.
     *
     * @param tourPackageService servicio que gestiona la lógica de negocio de paquetes
     */
    public HomeController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }


    /*
        Muestra el home por defecto
     */
    @GetMapping("")
    public String home() {

        return "home";
    }


    /**
     * Muestra la página principal del sitio.
     *
     * <p>Se muestran hasta 6 paquetes turísticos recientes y un paquete destacado
     * (en este caso, el paquete con ID 7).</p>
     *
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la vista del home
     */
    @GetMapping("/home")
    public String home(Model model) {
        List<TourPackageEntity> paquetes = tourPackageService.getAllPackages();
        if (paquetes.size() > 6) {
            paquetes = paquetes.subList(0, 6);
        }

        model.addAttribute("paquetes", paquetes);

        // Paquete destacado: Desierto de la Tatacoa (id = 7)
        TourPackageEntity paqueteDestacado = tourPackageService.getPackageById(7L);
        model.addAttribute("paqueteDestacado", paqueteDestacado);

        return "home";
    }

    /**
     * Muestra la vista de detalle para un paquete turístico específico.
     *
     * @param id ID del paquete a consultar
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la vista de detalle
     */
    @GetMapping("/paquetes/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        TourPackageEntity paquete = tourPackageService.getPackageById(id);
        model.addAttribute("paquete", paquete);
        return "detalle-paquete";
    }

    /**
     * Muestra la página de destinos turísticos del Huila.
     *
     * @return Nombre de la vista correspondiente al destino Huila
     */
    @GetMapping("/destinos/huila")
    public String huila() {
        return "destinos/huila";
    }

    /**
     * Muestra la página de destinos turísticos del Meta.
     *
     * @return Nombre de la vista correspondiente al destino Meta
     */
    @GetMapping("/destinos/meta")
    public String meta() {
        return "destinos/meta";
    }

    /**
     * Muestra la página de información institucional del sitio ("Nosotros").
     *
     * @return Nombre de la vista "nosotros"
     */
    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    /**
     * Muestra la página de contacto del sitio.
     *
     * @return Nombre de la vista "contacto"
     */
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}

