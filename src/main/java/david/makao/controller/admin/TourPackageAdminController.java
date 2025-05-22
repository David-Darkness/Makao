package david.makao.controller.admin;

import david.makao.model.TourPackageEntity;
import david.makao.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/paquetes")
public class TourPackageAdminController {

    @Autowired
    private TourPackageService tourPackageService;

    @GetMapping
    public String listarPaquetes(Model model) {
        model.addAttribute("paquetes", tourPackageService.getAllPackages());
        return "admin/paquetes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paquete", new TourPackageEntity());
        return "admin/paquetes-form";
    }

    @PostMapping("/guardar")
    public String guardarPaquete(@ModelAttribute TourPackageEntity paquete) {
        if (paquete.getPackageId() == null) {
            tourPackageService.createPackage(paquete);
        } else {
            tourPackageService.updatePackage(paquete);
        }
        return "redirect:/admin/paquetes";
    }

    @GetMapping("/editar/{id}")
    public String editarPaquete(@PathVariable Long id, Model model) {
        TourPackageEntity paquete = tourPackageService.getPackageById(id);
        if (paquete == null) {
            return "redirect:/admin/paquetes";
        }
        model.addAttribute("paquete", paquete);
        return "admin/paquetes-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPaquete(@PathVariable Long id) {
        tourPackageService.deletePackage(id);
        return "redirect:/admin/paquetes";
    }
}
