package david.makao.controller;

import org.springframework.ui.Model;
import david.makao.model.TourPackageEntity;
import david.makao.service.TourPackageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    private final TourPackageService tourPackageService;

    public HomeController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<TourPackageEntity> paquetes = tourPackageService.getAllPackages();
        if (paquetes.size() > 6) {
            paquetes = paquetes.subList(0, 6);
        }
        model.addAttribute("paquetes", paquetes);
        //paquete destacado Desierto de la tatacoa
        TourPackageEntity paqueteDestacado = tourPackageService.getPackageById(7L);
        model.addAttribute("paqueteDestacado", paqueteDestacado);

        return "home";
    }

    @GetMapping("/paquetes/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        TourPackageEntity paquete = tourPackageService.getPackageById(id);
        model.addAttribute("paquete", paquete);
        return "detalle-paquete";
    }

    @GetMapping("/destinos/huila")
    public String huila() {
        return "destinos/huila";
    }

    @GetMapping("/destinos/meta")
    public String meta() {
        return "destinos/meta";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}

